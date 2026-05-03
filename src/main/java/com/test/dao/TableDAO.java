package com.test.dao;

import com.test.model.TableDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TableDAO {

    /**
     * 1. 매장별 테이블 리스트 조회
     */
    List<TableDTO> getTableList(@Param("shopCode") String shopCode);

    /**
     * 2. 특정 매장의 모든 테이블 데이터 삭제 (Sync 1단계)
     */
    int deleteTablesByShop(@Param("shopCode") String shopCode);

    /**
     * 3. 테이블 추가 (Sync 2단계 및 단일 추가)
     */
    int insertTable(TableDTO dto);

    /**
     * 4. 단일 테이블 삭제
     */
    int deleteTable(@Param("tableIdx") int tableIdx, @Param("shopCode") String shopCode);
}