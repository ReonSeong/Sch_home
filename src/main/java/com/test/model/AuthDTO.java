/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-29
  Time: 오후 5:03
*/

package com.test.model;

public class AuthDTO {
        // 1. fields
        private int idx;            // index (Auto Increment)
        private String userId;      // login ID
        private String userPw;      // password
        private String userName;    // user name
        private String shopName;    // shop name
        private String shopCode;    // shop code for branches (ex: MAT001, MAT002 등)
        private String nation;      // country
        private String address;     // address
        private String role;        // auth for data (ADMIN, STAFF)
        private int mgrLevel;       // auth for data (ex: 1-biz owner, 2-branch owner)
        private String phone;       // user contact
        private String regDate;     // reg date
        private String lastLogin;   // last login

        // 2. 기본 생성자
        public AuthDTO() {}

        // 3. Getter 및 Setter 메서드
        public int getIdx() { return idx; }
        public void setIdx(int idx) { this.idx = idx; }

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }

        public String getUserPw() { return userPw; }
        public void setUserPw(String userPw) { this.userPw = userPw; }

        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }

        public String getShopName() { return shopName; }
        public void setShopName(String shopName) { this.shopName = shopName; }

        public String getShopCode() { return shopCode; }
        public void setShopCode(String shopCode) { this.shopCode = shopCode; }

        public String getNation() { return nation; }
        public void setNation(String nation) { this.nation = nation; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public int getMgrLevel() { return mgrLevel; }
        public void setMgrLevel(int mgrLevel) { this.mgrLevel = mgrLevel; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getRegDate() { return regDate; }
        public void setRegDate(String regDate) { this.regDate = regDate; }

        public String getLastLogin() { return lastLogin; }
        public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }

        // 4. toString() Overriding (for debug)
        @Override
        public String toString() {
            return "DTO [" +
                    "idx=" + idx +
                    ", userId='" + userId + '\'' +
                    ", userName='" + userName + '\'' +
                    ", shopName='" + shopName + '\'' +
                    ", shopCode='" + shopCode + '\'' +
                    ", nation='" + nation + '\'' +
                    ", mgrLevel=" + mgrLevel +
                    ']';
        }
}
