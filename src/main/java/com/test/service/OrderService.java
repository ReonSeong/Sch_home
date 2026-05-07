/**
 * File Name : SalesService.java
 *
 * Updated Date     Version     User        Change log
 * 2026-05-03              0.1     ReonQ       Published
 *
 * Now Version : 0.1
 *
 * Description:
 * SalesService.java
 */
package com.test.service;

import com.test.dao.OrderDAO;
import com.test.model.OrderDTO;
import com.test.model.OrderDTO.OrderDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderDAO orderDAO;

    /**
     * 전체 주문 내역 조회 (상세 포함)
     */
    public List<OrderDTO> getAllOrderList(String shopCode) {
        return orderDAO.selectAllOrdersWithDetail(shopCode);
    }

    /**
     * 주문 및 결제 등록 (마스터 + 상세 Batch Insert)
     */
    @Transactional(rollbackFor = Exception.class)
    public String registerOrder(OrderDTO orderDTO) throws Exception {

        // 1. 데이터 검증
        if (orderDTO.getItemList() == null || orderDTO.getItemList().isEmpty()) {
            logger.error("!!! [FAIL] 주문 상세 내역(itemList)이 비어있습니다. !!!");
            return "fail";
        }

        // 2. 주문 마스터 저장
        // MyBatis의 useGeneratedKeys="true" keyProperty="orderId" 설정 필요
        int masterResult = orderDAO.insertOrderMaster(orderDTO);

        if (masterResult > 0) {
            Integer generatedOrderId = orderDTO.getSaleId();
            logger.info(">>> 주문 마스터 등록 성공 [ID: {}]", generatedOrderId);

            // 3. 상세 내역에 생성된 마스터 ID(FK) 세팅
            for (OrderDetailDTO item : orderDTO.getItemList()) {
                item.setSaleId(generatedOrderId);
                item.setCreateUser(orderDTO.getCreateUser());
            }

            // 4. 주문 상세 내역 일괄 저장
            orderDAO.insertOrderDetails(orderDTO.getItemList());

            return "success";
        }

        return "fail";
    }
}