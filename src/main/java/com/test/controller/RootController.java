/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-10
  Time: 오후 4:53
*/

package com.test.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 스프링 MVC 컨트롤러 선언
public class RootController {

    private static final Logger logger = LoggerFactory.getLogger(RootController.class);

    @GetMapping("/")
    public String index(HttpSession session) {
        // 1. 세션 체크 (중앙 보안 로직)
        // 세션에 유저 정보가 이미 있다면 대시보드로 자동 이동시킵니다.
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard"; // DashboardController 주소로 리다이렉트
        }

        // 2. 세션이 없다면 로그인 페이지로 연결
        // ViewResolver가 "/WEB-INF/views/login.jsp"를 찾아 화면을 보여줍니다.
        return "login";
    }
}