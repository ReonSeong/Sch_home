package com.test.Controller;

import com.test.model.AuthDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.jstl.core.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        logger.info("======= [START] Dashboard Controller =======");

        // 1. 세션에서 유저 정보 가져오기
        AuthDTO sessionUser = (AuthDTO) session.getAttribute("user");

        // 유저 정보가 없으면 루트(/)로 리다이렉트
        if (sessionUser == null) {
            logger.warn("[AUTH] No session user found! Redirecting to Login");
            return "redirect:/";
        }

        // 2. 다국어 설정 로직 (기존 JSTL 설정 방식 유지)
        String lang = (String) session.getAttribute("lang");
        if (lang != null && !lang.isEmpty()) {
            Config.set(session, Config.FMT_LOCALE, new Locale(lang));
        } else {
            Config.set(session, Config.FMT_LOCALE, Locale.KOREAN);
        }

        // 3. 데이터를 Model에 담아 JSP로 전달
        // 기존 request.setAttribute("userInfo", sessionUser)와 동일한 역할입니다.
        model.addAttribute("userInfo", sessionUser);

        logger.info("[FORWARD] Forwarding to dashboard view");
        logger.info("======= [END] Dashboard Controller =======");

        // 4. JSP 파일명 반환
        return "dashboard";
    }
}