/**
 *  File Name : TableController.java
 *
 *  Updated Date     Version     User        Change log
 *  2026-04-19           0.1     ReonQ       Published
 *  2026-05-01           0.2     ReonQ       add Call DB to Check menuList
 *  2026-05-04           0.3     ReonQ       add Payment Process (Sales)
 *
 *  Now Version : 0.3
 *
 *  Description:
 *  TableController.java
 */

package com.test.controller;

import com.test.dao.MenuDAO;
import com.test.model.AuthDTO;
import com.test.model.MenuDTO;
import com.test.model.TableDTO;
import com.test.service.TableService;
import com.test.service.OrderService; // SalesService 추가
import com.test.util.JsonParser;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TableController {

    private static final Logger logger = LoggerFactory.getLogger(TableController.class);

    @Autowired
    private TableService tableService;

    @Autowired
    private OrderService orderService; // 추가

    @Autowired
    private MenuDAO menuDAO;

    // 1. 테이블 관리 페이지 진입
    @GetMapping("/table_status")
    public String getTableList(HttpSession session, Model model) {
        AuthDTO user = (AuthDTO) session.getAttribute("user");
        if (user == null) return "redirect:/";

        String shopCode = user.getShopCode();

        List<TableDTO> tableList = tableService.getTableList(shopCode);
        model.addAttribute("tableList", tableList);

        List<MenuDTO> menuList = menuDAO.selectAllMenuList(shopCode);
        String menuListJson = JsonParser.toJson(menuList);
        model.addAttribute("menuListJson", menuListJson);

        return "table_status";
    }

    // 2. 테이블 배치 정보 동기화 (AJAX)
    @PostMapping("/table/saveLayout")
    @ResponseBody
    public String saveLayout(@RequestBody List<TableDTO> layoutData, HttpSession session) {
        AuthDTO user = (AuthDTO) session.getAttribute("user");
        if (user == null) return "fail: unauthorized";

        boolean result = tableService.syncTableLayout(user.getShopCode(), layoutData);
        return result ? "success" : "fail";
    }

//    // 3. 결제 처리 및 매출 등록 (AJAX) 추가
//    @PostMapping("/table/processPayment")
//    @ResponseBody
//    public String processPayment(@RequestBody SalesDTO salesDTO, HttpSession session) {
//        AuthDTO user = (AuthDTO) session.getAttribute("user");
//        if (user == null) return "unauthorized";
//
//        try {
//            // 기본 정보 세팅
//            salesDTO.setShopCode(user.getShopCode());
//            salesDTO.setCreateUser(user.getUserId());
//
//            return salesService.registerSales(salesDTO); // "success" or "fail"
//        } catch (Exception e) {
//            logger.error("*****TableController processPayment Error*****", e.getMessage());
//            return "error";
//        }
//    }
}