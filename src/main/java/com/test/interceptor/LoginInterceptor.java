/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-29
  Time: 오후 6:39
*/

package com.test.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String pureURI = requestURI.replace(contextPath, "");

        // 1. 제외할 경로 정의 (로그인, 인증 관련 + 정적 리소스 추가)
        if (pureURI.equals("/") ||
                pureURI.equals("") ||
                pureURI.contains("/auth/") ||
                pureURI.startsWith("/resources/")) { // <-- 이 줄을 반드시 추가!
            return true;
        }

        // 2. 세션 체크
        if (session.getAttribute("user") == null) {
            logger.warn("비로그인 사용자 접근 차단: {}", requestURI);
            response.sendRedirect(contextPath + "/");
            return false;
        }

        return true;
    }
}