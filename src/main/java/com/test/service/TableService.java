/**
 *  File Name : TableService.java
 *
 *  Updated Date     Version     User        Change log
 *  2026-05-01           0.1     ReonQ       Published
 *
 *  Now Version : 0.1
 *
 *  Description:
 *  TableService.java
 */


package com.test.service;

import com.test.dao.TableDAO;
import com.test.interceptor.LoginInterceptor;
import com.test.model.TableDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TableService {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private TableDAO tableDAO;

    /**
     * 테이블 배치 정보 동기화
     * (기존 데이터 삭제 -> 새로운 데이터 일괄 삽입)
     */
    @Transactional
    public boolean syncTableLayout(String shopCode, List<TableDTO> layoutData) {
        try {
            // 1. 기존 해당 매장 테이블 삭제
            tableDAO.deleteTablesByShop(shopCode);

            // 2. 새로운 데이터 삽입
            for (TableDTO dto : layoutData) {
                dto.setShopCode(shopCode); // 매장 코드 강제 주입 (보안)
                tableDAO.insertTable(dto);
            }
            return true;
        } catch (Exception e) {
            logger.error("*****TableService syncTableLayout Error*****", e.getMessage());
            return false;
        }
    }

    /**
     * 테이블 목록 조회
     */
    public List<TableDTO> getTableList(String shopCode) {
        return tableDAO.getTableList(shopCode);
    }
}