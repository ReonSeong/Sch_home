/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-10
  Time: 오후 5:17
*/

package com.test.Model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthDAO {

    // DB 연결을 위한 DataSource 얻기 (싱글톤 패턴이나 공통 메서드로 관리하면 좋음)
    private Connection getConnection() throws Exception {
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/myoracle");
        return ds.getConnection();
    }

    /**
     * 로그인 체크 메서드
     * @param id 사용자가 입력한 ID
     * @param pw 사용자가 입력한 PW
     * @return 로그인 성공 시 회원 정보가 담긴 DTO, 실패 시 null
     */
    public DTO loginCheck(String id, String pw) {
        DTO user = null;
        String sql = "SELECT * FROM MST_USER WHERE USER_ID = ? AND USER_PW = ?";

        // try-with-resources 구문을 사용하여 자원을 자동으로 닫습니다.
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, pw);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new DTO();
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
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}