/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-10
  Time: 오후 9:46
*/

package com.test.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver; // 인터페이스 임포트

import java.util.Locale;

@Controller
public class LanguageController {

    // XML에 등록한 localeResolver 빈을 직접 주입받습니다.
    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/changeLang")
    public String changeLang(@RequestParam("lang") String lang,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        // 1. 지원 언어 체크 (보안 및 에러 방지)
        String targetLang = lang.toLowerCase();
        if (!targetLang.equals("ko") && !targetLang.equals("sr") && !targetLang.equals("en")) {
            targetLang = "en";
        }

        Locale locale = new Locale(targetLang);

        // 2. 중요: 주입받은 localeResolver를 직접 사용하여 세션에 저장
        // 이 코드가 실행될 때 더 이상 Accept-Language 헤더를 건드리지 않습니다.
        localeResolver.setLocale(request, response, locale);

        // 3. UI 표시용 세션 값 동기화
        request.getSession().setAttribute("currentLang", targetLang);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/dashboard");
    }
}