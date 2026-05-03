package com.test.Controller;

import com.test.model.AuthDTO;
import com.test.service.DashboardService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private DashboardService dashboardService;

    /**
     * 대시보드 메인
     * 세션 체크 및 다국어 설정은 LoginInterceptor에서 전담합니다.
     */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        logger.info("======= [DASHBOARD] 진입 =======");

        // 1. 필요한 데이터 추출 (인터셉터를 통과했으므로 user는 무조건 존재함)
        AuthDTO sessionUser = (AuthDTO) session.getAttribute("user");

        // 2. 대시보드 전용 데이터 준비 (Service 호출)
        dashboardService.prepareDashboardData(sessionUser);

        // 3. View에 데이터 전달
        model.addAttribute("userInfo", sessionUser);

        logger.info("[DASHBOARD] 사용자 {} 화면 이동", sessionUser.getUserId());
        return "dashboard";
    }
}