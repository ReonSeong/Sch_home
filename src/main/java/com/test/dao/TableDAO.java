package com.test.dao;

import com.test.model.TableDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDAO extends BaseDAO {

    /**
     * 1. 매장별 테이블 리스트 조회
     * @param shopCode 매장 고유 코드
     * @return 테이블 리스트
     */
    public List<TableDTO> getTableList(String shopCode) {
        List<TableDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM MST_TABLE_LAYOUT WHERE SHOP_CODE = ? ORDER BY TABLE_IDX ASC";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, shopCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TableDTO dto = new TableDTO();
                    dto.setTableIdx(rs.getInt("TABLE_IDX"));
                    dto.setShopCode(rs.getString("SHOP_CODE"));
                    dto.setTableName(rs.getString("TABLE_NAME"));
                    dto.setPosX(rs.getInt("POS_X"));
                    dto.setPosY(rs.getInt("POS_Y"));
                    dto.setStatus(rs.getString("STATUS"));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 2. 테이블 배치 정보 일괄 동기화 (Sync)
     * 로직: 기존 해당 매장의 테이블을 모두 삭제 후, 현재 화면의 상태를 새롭게 저장합니다.
     * @param shopCode 매장 고유 코드
     * @param layoutData 화면에서 넘어온 최종 테이블 리스트
     * @return 성공 여부
     */
    public boolean syncTableLayout(String shopCode, List<TableDTO> layoutData) {
        String deleteSql = "DELETE FROM MST_TABLE_LAYOUT WHERE SHOP_CODE = ?";
        String insertSql = "INSERT INTO MST_TABLE_LAYOUT (SHOP_CODE, TABLE_NAME, POS_X, POS_Y, STATUS) VALUES (?, ?, ?, ?, 'EMPTY')";

        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작

            // [STEP 1] 기존 해당 매장의 모든 테이블 데이터 삭제
            try (PreparedStatement dstmt = conn.prepareStatement(deleteSql)) {
                dstmt.setString(1, shopCode);
                dstmt.executeUpdate();
            }

            // [STEP 2] 새로운 배치 리스트 일괄 삽입 (Batch 처리)
            try (PreparedStatement istmt = conn.prepareStatement(insertSql)) {
                for (TableDTO dto : layoutData) {
                    istmt.setString(1, shopCode);
                    istmt.setString(2, dto.getTableName() != null ? dto.getTableName() : "Unnamed");
                    istmt.setInt(3, dto.getPosX());
                    istmt.setInt(4, dto.getPosY());
                    istmt.addBatch();
                }
                istmt.executeBatch();
            }

            conn.commit(); // 모든 과정 성공 시 커밋
            return true;

        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    /**
     * (참고용) 기존 단일 추가/삭제 메서드는 유지하거나 필요 없으면 삭제해도 됩니다.
     * 현재 "일괄 저장" 방식에서는 위의 syncTableLayout이 모든 역할을 수행합니다.
     */

    public boolean insertTable(TableDTO dto) {
        String sql = "INSERT INTO MST_TABLE_LAYOUT (SHOP_CODE, TABLE_NAME, POS_X, POS_Y, STATUS) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dto.getShopCode());
            pstmt.setString(2, dto.getTableName());
            pstmt.setInt(3, dto.getPosX());
            pstmt.setInt(4, dto.getPosY());
            pstmt.setString(5, dto.getStatus());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTable(int tableIdx, String shopCode) {
        String sql = "DELETE FROM MST_TABLE_LAYOUT WHERE TABLE_IDX = ? AND SHOP_CODE = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tableIdx);
            pstmt.setString(2, shopCode);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}