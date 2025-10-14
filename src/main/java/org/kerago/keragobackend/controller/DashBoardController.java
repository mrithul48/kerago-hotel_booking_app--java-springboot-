package org.kerago.keragobackend.controller;

import org.kerago.keragobackend.dto.DashBoardDto;
import org.kerago.keragobackend.service.DashboardStatsResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("v1/chart")
public class DashBoardController {
    @Autowired
    DashboardStatsResponseService dashboardStatsResponseService;

    @GetMapping
    public ResponseEntity<DashBoardDto> getPichart(){
       return ResponseEntity.ok(dashboardStatsResponseService.getStatus());
    }
}
