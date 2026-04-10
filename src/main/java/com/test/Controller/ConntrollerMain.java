package com.test.Controller;

import com.test.Model.DTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.http.HttpSession;

// DB
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
        logger.info("***Controller -- doGet! ***");


        // 세션에서 로그인 정보 가져오기 (로그인 체크용)
        HttpSession session = request.getSession();
        DTO sessionUser = (DTO) session.getAttribute("user");

        if (sessionUser == null) {
            // 로그인 정보가 없으면 로그인 페이지로 튕겨내기
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 1. JNDI를 통해 context.xml에 설정된 리소스 찾기
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            // "jdbc/myoracle"은 web.xml과 context.xml에 적은 이름과 일치
            DataSource ds = (DataSource) envContext.lookup("jdbc/myoracle");

            // 2. 연결 얻기 (DriverManager.getConnection 대신 ds.getConnection 사용)
            conn = ds.getConnection();
            logger.debug("DB connection established");

            // 3. 쿼리 실행
            String sql = "SELECT * FROM MST_USER WHERE USER_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sessionUser.getUserId()); // 세션에 저장된 ID 활용

            rs = pstmt.executeQuery();

            if (rs.next()) {
                DTO user = new DTO();

                // 1. basic identify
                user.setIdx(rs.getInt("IDX"));
                user.setUserId(rs.getString("USER_ID"));
                user.setUserName(rs.getString("USER_NAME"));

                // 2. shop info
                user.setShopName(rs.getString("SHOP_NAME"));
                user.setShopCode(rs.getString("SHOP_CODE"));
                user.setNation(rs.getString("NATION"));
                user.setAddress(rs.getString("ADDRESS"));

                // 3. auth check
                user.setRole(rs.getString("ROLE"));
                user.setMgrLevel(rs.getInt("MGR_LEVEL"));
                user.setPhone(rs.getString("PHONE"));

                // 4. date check
                user.setRegDate(rs.getString("REG_DATE"));
                user.setLastLogin(rs.getString("LAST_LOGIN"));

                // 로그로 확인 (선택 사항)
                logger.debug("DTO 매핑 완료: " + user.getUserId() + " / " + user.getShopName());

                request.setAttribute("userInfo", user);

            }

        } catch (Exception e) {
            logger.error("DB error - 1001", e);
            e.printStackTrace();
        } finally {
            // 4. 자원 반납 (중요: 커넥션 풀을 쓸 때는 반드시 닫아줘야 다시 풀로 돌아갑니다)
            try { if(rs != null) rs.close(); } catch(Exception e) {}
            try { if(pstmt != null) pstmt.close(); } catch(Exception e) {}
            try { if(conn != null) conn.close(); } catch(Exception e) {}
        }

        // JSP 포워딩
        request.setAttribute("name", "World");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard.jsp");
        dispatcher.forward(request, response);
    }
}