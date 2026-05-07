/**
 * File Name : StockController.java
 * <p>
 * Updated Date     Version     User        Change log
 * 2026-05-08              0.1     ReonQ       Published
 * <p>
 * Now Version : 0.1
 * <p>
 * Description:
 * StockController.java
 */

package com.test.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stock")
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(RootController.class);

    @GetMapping("/status")
    public String goStockManagePage() {
        logger.info(">>> 재료 관리 페이지(stock_status.jsp) 이동");

        return "stock_status";
    }
}
