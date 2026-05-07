/**
 *  File Name : LanguageService.java
 *
 *  Updated Date     Version     User        Change log
 *  2026-05-01           0.1     ReonQ       Published
 *
 *  Now Version : 0.1
 *
 *  Description:
 *  LanguageService.java
 */

package com.test.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

@Service
public class LanguageService {

    public void changeLocale(String lang, HttpServletRequest request, HttpServletResponse response) {
        if (lang != null && !lang.isEmpty()) {
            Locale locale = new Locale(lang);
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver != null) {
                // 세션에 로케일 정보 저장 (스프링이 관리)
                localeResolver.setLocale(request, response, locale);
                // 커스텀 세션 값이 필요하다면 추가 저장
                request.getSession().setAttribute("currentLang", lang);
            }
        }
    }
}