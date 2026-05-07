/**
 * File Name : SalesDAO.java
 *
 * Updated Date     Version     User        Change log
 * 2026-05-03           0.1     ReonQ       Published
 * 2026-05-07           0.2     ReonQ       Refactor Sales to Order
 *
 * Now Version : 0.1
 *
 * Description:
 * SalesDAO.java
 */

package com.test.dao;

import com.test.model.OrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface OrderDAO {

    /**
     * 판매 마스터 정보 저장
     */
    int insertOrderMaster(OrderDTO orderDTO);

    /**
     * 판매 상세 내역 일괄 저장 (Batch)
     */
    int insertOrderDetails(List<OrderDTO.OrderDetailDTO> itemList);

    /**
     * 전체 판매 내역 조회 (상세 내역 포함 JOIN)
     */
    List<OrderDTO> selectAllOrdersWithDetail(@Param("shopCode") String shopCode);
}
