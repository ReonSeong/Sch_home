package com.test.Controller;

import com.test.Model.DTO;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
//DB
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;     // DB 에러 처리를 위해 필요!

//Logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// 1. 주소 매핑: 사용자가 어떤 주소로 들어올 때 이 컨트롤러를 실행할지 결정합니다.
// 1. Adderes(URL) mapping
@WebServlet("/hello")
public class ConntrollerMain extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ConntrollerMain.class);

    // 2. GET 방식의 요청을 처리하는 메서드
    // 2. Method to deal with GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        logger.info("컨트롤러 진입 성공!");// 진입 확인

        logger.info("doGet 진입 성공!");

        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "1234";

        try {
            // 1. 드라이버 로드 (Tomcat 10 환경에서는 생략 가능하나 명시하면 안전함)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("드라이버 로드");

            // 2. 연결
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("DB연결");

            // 3. 쿼리 실행 (DAY01 테이블 조회)
            String sql = "SELECT * FROM DAY01 LIMIT 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("쿼리 실행");


            if (rs.next()) {
                DTO dto = new DTO();
                dto.setId(rs.getString("id"));     // DB 컬럼명 확인 필수!
                dto.setName(rs.getString("name"));

                System.out.println(dto.toString());

                // 4. JSP로 전달
                request.setAttribute("user", dto);
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            request.setAttribute("name", "World");

            // 2. 연결할 JSP 파일 경로 지정
            // webapp 폴더 바로 아래에 index.jsp가 있다면 "/index.jsp"
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");

            // 3. 실제로 넘기기 (Forward)
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. 실제 처리를 담당할 커스텀 메서드 (가독성을 위해 분리)
    // 3. Custom method
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // [비즈니스 로직 예시]
        String message = "드디어 첫 컨트롤러가 작동합니다!";

        // [Model에 데이터 담기]
        request.setAttribute("msg", message);

        // [View로 보내기]
        request.getRequestDispatcher("/view.jsp").forward(request, response);
    }
}