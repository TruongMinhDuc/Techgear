package com.techgear.techgear_be.controllers.statistic;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.dtos.statistic.StatisticResponse;
import com.techgear.techgear_be.services.statistic.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class StatisticController {

    private StatisticService statisticService;

    @GetMapping
    public ResponseEntity<StatisticResponse> getStatistic() {
        return ResponseEntity.status(HttpStatus.OK).body(statisticService.getStatistic());
    }

}
