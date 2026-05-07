/**
 * File Name : StatsDTO.java
 * <p>
 * Updated Date     Version     User        Change log
 * 2026-05-07              0.1     ReonQ       Published
 * <p>
 * Now Version : 0.1
 * <p>
 * Description:
 * StatsDTO.java
 */

package com.test.model;


import lombok.Data;
import java.math.BigDecimal;

@Data
public class StatsDTO {
    private String label;          // 날짜, 시간, 또는 메뉴명
    private Long count;            // 결제 건수 또는 수량
    private BigDecimal totalAmount; // 총 매출액
}
