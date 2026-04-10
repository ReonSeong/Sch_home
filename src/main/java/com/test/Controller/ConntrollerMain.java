package com.test.Controller;

import com.test.Model.DTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.jstl.core.Config; // JSTL 설정용

import java.io.IOException;
import java.util.Locale; // 로케일 설정용

// DB 관련
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

// Logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/dashboard")
public class ConntrollerMain extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ConntrollerMain.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // [LOG] 대시보드 진입 시점 기록
        logger.info("======= [START] Dashboard Servlet =======");

        // 1. 세션 및 로그인 체크
        HttpSession session = request.getSession();
        DTO sessionUser = (DTO) session.getAttribute("user");

        if (sessionUser == null) {
            logger.warn("[AUTH] No session user found! Redirecting to login.jsp");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 2. [핵심 디버깅] 세션 언어 설정 확인 및 JSTL 로케일 강제 주입
        String lang = (String) session.getAttribute("lang");

        // [LOG] 세션에 저장된 언어값이 무엇인지 확인
        logger.info("[LANG] Session 'lang' value: {}", lang);

        if (lang != null && !lang.isEmpty()) {
            Locale locale = new Locale(lang);
            // JSTL이 브라우저 언어 무시하고 세션 언어를 쓰도록 강제 설정
            Config.set(session, Config.FMT_LOCALE, locale);

            // [LOG] JSTL 설정 주입 확인
            logger.debug("[LANG] SUCCESS: Forced JSTL Locale to '{}'", locale.toString());
        } else {
            // [LOG] 언어 설정이 없을 때의 경고
            logger.debug("[LANG] DEFAULT: No lang found in session, using default locale.");
            Config.set(session, Config.FMT_LOCALE, Locale.KOREAN);
        }

        // 3. DB 로직 시작
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/myoracle");

            conn = ds.getConnection();
            logger.debug("[DB] Connection established successfully.");

            String sql = "SELECT * FROM MST_USER WHERE USER_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sessionUser.getUserId());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                DTO user = new DTO();

                // User Identity & Shop Info Mapping
                user.setIdx(rs.getInt("IDX"));
                user.setUserId(rs.getString("USER_ID"));
                user.setUserName(rs.getString("USER_NAME"));
                user.setShopName(rs.getString("SHOP_NAME"));
                user.setShopCode(rs.getString("SHOP_CODE"));
                user.setNation(rs.getString("NATION"));
                user.setAddress(rs.getString("ADDRESS"));
                user.setRole(rs.getString("ROLE"));
                user.setMgrLevel(rs.getInt("MGR_LEVEL"));
                user.setPhone(rs.getString("PHONE"));
                user.setRegDate(rs.getString("REG_DATE"));
                user.setLastLogin(rs.getString("LAST_LOGIN"));

                logger.debug("[DB] User data mapping complete for ID: {}", user.getUserId());
                request.setAttribute("userInfo", user);
            }

        } catch (Exception e) {
            logger.error("[DB ERROR] Dashboard data fetch failed!", e);
        } finally {
            try { if(rs != null) rs.close(); } catch(Exception e) { logger.error("ResultSet close error", e); }
            try { if(pstmt != null) pstmt.close(); } catch(Exception e) { logger.error("PreparedStatement close error", e); }
            try { if(conn != null) conn.close(); } catch(Exception e) { logger.error("Connection close error", e); }
        }

        // 4. JSP 포워딩
        logger.info("[FORWARD] Forwarding request to dashboard.jsp");
        logger.info("======= [END] Dashboard Servlet =======");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard.jsp");
        dispatcher.forward(request, response);
    }
}