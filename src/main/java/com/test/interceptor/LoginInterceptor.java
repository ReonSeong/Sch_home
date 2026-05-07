package com.test.interceptor;

import com.test.model.AuthDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String pureURI = requestURI.replace(contextPath, "");

        // 1. 체크 제외 경로 (로그인 페이지, 정적 리소스, 언어 변경 API 등)
        if (pureURI.equals("/") ||
                pureURI.equals("") ||
                pureURI.contains("/login") ||
                pureURI.contains("/changeLang") ||
                pureURI.startsWith("/resources/")) {
            return true;
        }

        // 2. 로그인 세션 체크
        AuthDTO user = (AuthDTO) session.getAttribute("user");
        if (user == null) {
            logger.warn("비로그인 사용자 접근 차단: {}", requestURI);
            response.sendRedirect(contextPath + "/");
            return false;
        }

        // 3. [추가] ShopCode 세션 관리
        // 세션에 shopCode가 없거나, 로그인된 정보와 다르다면 동기화
        String sessionShopCode = (String) session.getAttribute("shopCode");
        if (sessionShopCode == null || !sessionShopCode.equals(user.getShopCode())) {
            session.setAttribute("shopCode", user.getShopCode());
            logger.info("[SESSION] 매장 코드 세팅 완료: {}", user.getShopCode());
        }

        // 4. 다국어 설정 동기화 로직
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (localeResolver != null) {
            String sessionLang = (String) session.getAttribute("currentLang");
            if (sessionLang != null) {
                Locale currentLocale = localeResolver.resolveLocale(request);
                if (!currentLocale.getLanguage().equals(sessionLang)) {
                    localeResolver.setLocale(request, response, new Locale(sessionLang));
                    logger.info("[LOCALE] 언어 설정 동기화: {}", sessionLang);
                }
            }
        }

        return true;
    }
}