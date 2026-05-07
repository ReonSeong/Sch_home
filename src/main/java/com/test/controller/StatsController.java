/**
 * File Name : StatsController.java
 * <p>
 * Updated Date     Version     User        Change log
 * 2026-05-07              0.1     ReonQ       Published
 * <p>
 * Now Version : 0.1
 * <p>
 * Description:
 * StatsController.java
 */

package com.test.controller;

import com.test.dao.StatsDAO;
import com.test.model.StatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private StatsDAO statsDAO;

    // 일별 매출 통계: ("month") 명시
    @GetMapping("/daily")
    public List<StatsDTO> getDailyStats(@RequestParam("month") String month) {
        return statsDAO.selectDailyStats(month);
    }

    // 시간대별 매출 통계: ("date") 명시
    @GetMapping("/hourly")
    public List<StatsDTO> getHourlyStats(@RequestParam("date") String date) {
        return statsDAO.selectHourlyStats(date);
    }

    // 메뉴별 상세 통계: ("date"), ("hour") 명시
    @GetMapping("/menu")
    public List<StatsDTO> getMenuStats(@RequestParam("date") String date,
                                       @RequestParam("hour") String hour) {
        return statsDAO.selectMenuStats(date, hour);
    }
}
