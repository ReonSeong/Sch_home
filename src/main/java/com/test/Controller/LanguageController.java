/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-10
  Time: 오후 9:46
*/

package com.test.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// Logging 추가
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/changeLang")
public class LanguageController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 로거 선언
    private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String lang = request.getParameter("lang");

        // [LOG] 파라미터 유입 확인
        logger.info(">>> Language Change Request: {}", lang);

        if (lang != null && !lang.isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("lang", lang);

            // [LOG] 세션 저장 확인
            logger.info("Language successfully saved in session: {}", session.getAttribute("lang"));
        } else {
            logger.warn("Language parameter is missing or empty!");
        }

        String referer = request.getHeader("Referer");
        logger.debug("Redirecting back to: {}", referer);

        if (referer != null && !referer.isEmpty()) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}