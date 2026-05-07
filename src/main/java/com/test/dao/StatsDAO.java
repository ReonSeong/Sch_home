/**
 * File Name : StatsDAO.java
 * <p>
 * Updated Date     Version     User        Change log
 * 2026-05-07              0.1     ReonQ       Published
 * <p>
 * Now Version : 0.1
 * <p>
 * Description:
 * StatsDAO.java
 */

package com.test.dao;

import com.test.model.StatsDTO; // 확인
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface StatsDAO {
    // List<StatsDAO> -> List<StatsDTO> 로 수정 완료
    List<StatsDTO> selectDailyStats(@Param("month") String month);
    List<StatsDTO> selectHourlyStats(@Param("date") String date);
    List<StatsDTO> selectMenuStats(@Param("date") String date, @Param("hour") String hour);
}