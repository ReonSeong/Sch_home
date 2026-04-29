/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-29
  Time: 오후 6:39
*/

package com.test.Interceptor;

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

        // 1. 제외할 경로 정의 (Context Path 제외 순수 경로)
        String pureURI = requestURI.replace(contextPath, "");

        // 2. 루프 방지: 루트("/")나 로그인 처리 경로("/auth/login")는 체크 없이 통과
        if (pureURI.equals("/") || pureURI.equals("") || pureURI.contains("/auth/")) {
            return true;
        }

        if (session.getAttribute("user") == null) {
            logger.warn("세션 없음: {} -> 루트로 리다이렉트", requestURI);
            response.sendRedirect(contextPath + "/");
            return false;
        }

        return true;
    }
}