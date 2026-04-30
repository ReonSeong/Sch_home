/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-30
  Time: 오후 5:19
*/

package com.test.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MenuDTO {
    // MST_menu_info 필드
    private int menuIdx;
    private String shopCode;
    private String category;
    private String menuName;

    // 추가 필드
    private int price;
    private String currency;

    private String imagePath;
    private String description;

    // DTL_menu_info 필드
    private String ingredients;

    // 관리 필드
    private String regUser;
    private LocalDateTime regDate;
    private String updateUser;
    private LocalDateTime updateDate;
}