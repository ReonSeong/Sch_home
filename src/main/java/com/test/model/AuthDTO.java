package com.test.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 사용자 인증 및 권한 정보를 담당하는 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {
    private int idx;            // 고유 인덱스
    private String userId;      // 로그인 ID
    private String userPw;      // 비밀번호 (DB 조회 시 필요)
    private String userName;    // 사용자 이름
    private String shopName;    // 매장 이름
    private String shopCode;    // 매장 코드 (MAT001 등)
    private String nation;      // 국가
    private String address;     // 주소
    private String role;        // 권한 (ADMIN, STAFF)
    private int mgrLevel;       // 관리 레벨 (1: 점주, 2: 관리자 등)
    private String phone;       // 연락처
    private String regDate;     // 등록일
    private String lastLogin;   // 마지막 로그인 시간
}