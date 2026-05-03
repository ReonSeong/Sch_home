/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-05-01
  Time: 오후 6:25
*/

package com.test.service;

import com.test.model.AuthDTO;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    /**
     * 대시보드 진입 시 필요한 추가 데이터 가공이 필요하다면 여기서 처리합니다.
     * 예: 오늘 매출 요약, 예약 현황 등
     */
    public void prepareDashboardData(AuthDTO user) {
        // 현재는 특별한 로직이 없으나, 향후 DB 조회가 필요할 때 이곳에 작성합니다.
    }
}