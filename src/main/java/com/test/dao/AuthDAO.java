/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-10
  Time: 오후 5:17
*/

package com.test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.test.model.AuthDTO;

// 여기에 extends BaseDAO를 추가해서 getConnection()을 상속받습니다!
public class AuthDAO extends BaseDAO {

    public AuthDTO loginCheck(String id, String pw) {
        AuthDTO user = null;
        String sql = "SELECT * FROM MST_USER WHERE USER_ID = ? AND USER_PW = ?";

        // 이제 아래의 getConnection() 호출이 에러 없이 작동합니다.
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, pw);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new AuthDTO();
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