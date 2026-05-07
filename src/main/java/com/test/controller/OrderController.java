/**
 * File Name : SalesController.java
 *
 * Updated Date     Version     User        Change log
 * 2026-05-03           0.1     ReonQ       Published
 * 2026-05-07           0.2     ReonQ       Refactored: Sales to Order
 *
 * Now Version : 0.1
 *
 * Description:
 * SalesController.java
 */

package com.test.controller;

import com.test.model.AuthDTO;
import com.test.model.OrderDTO;
import com.test.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order") // 관련 API를 /order 하위로 묶어 관리합니다.
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 1. 결제 처리 및 매출 데이터 저장 (AJAX)
     * 프론트에서 넘어온 Master + Detail 정보를 한 번에 저장합니다.
     */
    @PostMapping("/payment")
    @ResponseBody
    public String processPayment(@RequestBody OrderDTO orderDTO, HttpSession session) {
        AuthDTO user = (AuthDTO) session.getAttribute("user");
        if (user == null) return "fail: unauthorized";

        try {
            orderDTO.setShopCode(user.getShopCode());
            orderDTO.setCreateUser(user.getUserId());

            // 추가: finalPrice가 비어있다면 totalPrice 값으로 세팅 (할인이 없는 경우)
            if (orderDTO.getFinalPrice() == null) {
                orderDTO.setFinalPrice(orderDTO.getTotalPrice());
            }

            // 추가: 결제 수단 기본값 처리 (없을 경우 CARD 등)
            if (orderDTO.getPayMethod() == null) {
                orderDTO.setPayMethod("Cash");
            }

            return orderService.registerOrder(orderDTO);

        } catch (Exception e) {
            logger.error("*****Sales Controller payment Error*****", e);
            return "error: " + e.getMessage();
        }
    }

    /**
     * 2. 전체 매출 내역 조회 (AJAX)
     * 로그인한 사용자의 매장 코드에 해당하는 매출 목록을 가져옵니다.
     */
    @GetMapping("/getSalesList")
    @ResponseBody
    public List<OrderDTO> getSalesList(HttpSession session) {
        AuthDTO user = (AuthDTO) session.getAttribute("user");
        if (user == null) return null;

        // 매장 코드에 따른 매출 리스트 반환
        return orderService.getAllOrderList(user.getShopCode());
    }
}