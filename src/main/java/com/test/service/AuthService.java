/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-05-01
  Time: 오후 5:47
*/

package com.test.service;

import com.test.dao.AuthDAO;
import com.test.model.AuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private AuthDAO authDAO;

    @Transactional
    public AuthDTO login(String id, String pw) {
        // 1. 유저 정보 조회
        AuthDTO user = authDAO.loginCheck(id, pw);

        // 2. 유저가 존재하면 로그인 시간 업데이트
        if (user != null) {
            authDAO.updateLastLogin(id);
        }

        return user;
    }
}