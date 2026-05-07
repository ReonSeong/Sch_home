/**
 * File Name : SalesDTO.java
 * Updated Date     Version     User        Change log
 * 2026-05-03           0.1     ReonQ       Published
 *
 * Now Version : 0.1
 *
 * Description:
 * SalesDTO.java
 */

package com.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    // --- Master 정보 ---
    private Integer saleId;
    private String shopCode;
    private Integer tableIdx;
    private BigDecimal totalPrice;
    private BigDecimal finalPrice;
    private String payMethod;

    private String createUser;
    private LocalDateTime createTime;
    private String updateUser;
    private LocalDateTime updateTime;
    private String delYn;

    private String att1;
    private String att2;
    private String att3;
    private String att4;
    private String att5;

    // --- Detail 목록 (병합의 핵심) ---
    @JsonProperty("itemList")
    private List<OrderDetailDTO> itemList;

    // --- Inner Class: 상세 정보 ---
    @Data
    public static class OrderDetailDTO {
        private Integer detailId;
        private Integer saleId;
        private Integer menuIdx;
        private String menuName;
        private BigDecimal unitPrice;
        private Integer quantity;
        private String createUser;
        private LocalDateTime createTime;
        private String updateUser;
        private LocalDateTime updateTime;

        private String att1;
        private String att2;
        private String att3;
        private String att4;
        private String att5;
    }
}