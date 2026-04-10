/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-10
  Time: 오후 5:18
*/
package com.test.Controller;

import com.test.Model.AuthDAO;
import com.test.Model.DTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

// /auth/login 또는 /auth/logout으로 들어오는 모든 요청을 처리
@WebServlet("/auth/*")
public class AuthController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // URL 끝부분을 잘라서 명령(action) 확인
        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/") + 1);

        if ("login".equals(action)) {
            handleLogin(request, response);
        } else if ("logout".equals(action)) {
            handleLogout(request, response);
        }
    }

    // [로그인 처리]
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String userPw = request.getParameter("userPw");

        // 셰프(LoginDAO) 호출
        AuthDAO dao = new AuthDAO();
        DTO user = dao.loginCheck(userId, userPw);

        if (user != null) {
            // 로그인 성공: 세션에 유저 정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
        } else {
            // 로그인 실패: 에러 메시지와 함께 back
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=invalid");
        }
    }

    // [로그아웃 처리]
    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 파괴
        }
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}