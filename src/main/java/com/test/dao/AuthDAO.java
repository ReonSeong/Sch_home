/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-10
  Time: 오후 5:17
*/

package com.test.dao;

import com.test.model.AuthDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthDAO {

    // 로그인 체크 (ID, PW 일치 확인)
    AuthDTO loginCheck(@Param("userId") String userId, @Param("userPw") String userPw);

    // 로그인 성공 시 lastLogin 시간 업데이트
    int updateLastLogin(@Param("userId") String userId);
}