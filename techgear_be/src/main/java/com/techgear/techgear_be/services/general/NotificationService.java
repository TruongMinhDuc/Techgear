package com.techgear.techgear_be.services.general;

import com.techgear.techgear_be.dtos.general.NotificationResponse;

public interface NotificationService {

    void pushNotification(String uniqueKey, NotificationResponse notification);

}
