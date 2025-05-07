package com.techgear.techgear_be.services.general;

import com.techgear.techgear_be.dtos.general.NotificationResponse;
import com.techgear.techgear_be.mappers.general.EventMapper;
import com.techgear.techgear_be.repositories.general.EmitterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Primary
@AllArgsConstructor
@Slf4j
public class SseNotificationService implements NotificationService {

    private final EmitterRepository emitterRepository;
    private final EventMapper eventMapper;

    @Override
    public void pushNotification(String uniqueKey, NotificationResponse notification) {
        emitterRepository
                .getByUniqueKey(uniqueKey)
                .ifPresentOrElse(emitter -> {
                    try {
                        log.debug("Sending event: {} for key: {}", notification, uniqueKey);
                        emitter.send(eventMapper.toSseEventBuilder("message", notification));
                    } catch (IOException | IllegalStateException e) {
                        log.debug("Error while sending event: {} for key: {} - exception: {}", notification, uniqueKey, e);
                        emitterRepository.remove(uniqueKey);
                    }
                }, () -> log.debug("No emitter for key: {}", uniqueKey));
    }

}
