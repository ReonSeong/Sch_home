/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-05-01
  Time: 오후 5:34
*/

package com.test.service;

import com.test.dao.TableDAO;
import com.test.model.TableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TableService {

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
            // 로그 기록 후 롤백 유도
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