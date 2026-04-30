/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-29
  Time: 오후 5:10
*/

package com.test.model;

/**
 * 테이블의 물리적 위치와 상태 정보를 담당하는 데이터 이동 객체 (DTO)
 * 일괄 동기화 시 JSON 데이터와 매핑됩니다.
 */
public class TableDTO {
    private int tableIdx;      // 테이블 고유 번호 (기존 PK)
    private String shopCode;   // 매장 코드 (어떤 매장의 테이블인지 식별)
    private String tableName;  // 테이블 이름 (예: 1번 테이블, 창가석 등)
    private int posX;          // 화면상의 X 좌표 (px)
    private int posY;          // 화면상의 Y 좌표 (px)
    private String status;     // 테이블 상태 (EMPTY, USING, RESERVED 등)

    // 기본 생성자 (Jackson 라이브러리 등이 JSON 변환 시 필요)
    public TableDTO() {}

    /**
     * 편의를 위한 생성자
     */
    public TableDTO(String shopCode, String tableName, int posX, int posY, String status) {
        this.shopCode = shopCode;
        this.tableName = tableName;
        this.posX = posX;
        this.posY = posY;
        this.status = status;
    }

    // Getter / Setter
    public int getTableIdx() { return tableIdx; }
    public void setTableIdx(int tableIdx) { this.tableIdx = tableIdx; }

    public String getShopCode() { return shopCode; }
    public void setShopCode(String shopCode) { this.shopCode = shopCode; }

    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public int getPosX() { return posX; }
    public void setPosX(int posX) { this.posX = posX; }

    public int getPosY() { return posY; }
    public void setPosY(int posY) { this.posY = posY; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    /**
     * 로그 출력 및 디버깅을 위한 toString 오버라이딩
     */
    @Override
    public String toString() {
        return "TableDTO{" +
                "tableIdx=" + tableIdx +
                ", shopCode='" + shopCode + '\'' +
                ", tableName='" + tableName + '\'' +
                ", posX=" + posX +
                ", posY=" + posY +
                ", status='" + status + '\'' +
                '}';
    }
}