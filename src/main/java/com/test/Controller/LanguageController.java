/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-10
  Time: 오후 9:46
*/

package com.test.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.IOException;
import java.util.Locale;

@Controller // 1. 컨트롤러 선언
public class LanguageController {

    private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

    // 2. @GetMapping으로 변경 및 파라미터 자동 주입
    @GetMapping("/changeLang")
    public void changeLang(@RequestParam(value = "lang", required = false) String lang,
                           @RequestParam(value = "referer", required = false) String referer,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {

        if (lang != null && !lang.isEmpty()) {
            // 1. 세션에 명시적으로 언어 코드 저장
            request.getSession().setAttribute("currentLang", lang);

            // 2. LocaleResolver를 통해 스프링 엔진의 언어 설정 변경
            Locale locale = new Locale(lang);
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver != null) {
                // XML에 SessionLocaleResolver를 등록했으므로 이제 에러가 나지 않습니다.
                localeResolver.setLocale(request, response, locale);
            }
        }

        // 3. 리다이렉트 경로 결정 (루프 방지)
        String targetPath = (referer == null || referer.isEmpty() || referer.contains("/changeLang")) ? "/" : referer;

        // ContextPath 중복 방지 로직
        String cp = request.getContextPath();
        if (!targetPath.startsWith("http") && !targetPath.startsWith(cp)) {
            targetPath = cp + (targetPath.startsWith("/") ? "" : "/") + targetPath;
        }

        response.sendRedirect(targetPath);
    }
}