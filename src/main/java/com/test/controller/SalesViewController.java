/**
 * File Name : salesStatsController.java
 * <p>
 * Updated Date     Version     User        Change log
 * 2026-05-07              0.1     ReonQ       Published
 * <p>
 * Now Version : 0.1
 * <p>
 * Description:
 * To call JSP salesStatsController
 */

package com.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sales")
public class SalesViewController {

    private static final Logger logger = LoggerFactory.getLogger(SalesViewController.class);

    /**
     * 매출 관리 메인 페이지 호출
     * URL : http://localhost:8081/sales/status
     */
    @GetMapping("/status")
    public String goSalesManagePage() {
        logger.info(">>> 매출 관리 페이지(sales_status.jsp) 이동");

        // InternalResourceViewResolver 설정에 따라
        // /WEB-INF/views/sales_status.jsp 를 찾아갑니다.
        return "sales_status";
    }
}
