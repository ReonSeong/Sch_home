/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-30
  Time: 오후 5:21
*/

package com.test.dao;

import com.test.model.MenuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
public interface MenuDAO {

    // 1. 카테고리별 메뉴 리스트 조회 (MST만 조회하여 가볍게)
    List<MenuDTO> selectAllMenuList(String shopcode);

    // 2. 메뉴 상세 조회 (MST + DTL Join)
    MenuDTO selectMenuDetail(@Param("menuIdx") int menuIdx, @Param("shopCode") String shopCode);

    // 3. 신규 메뉴 등록 (MST)
    int insertMenuMst(MenuDTO menuDTO);

    // 4. 신규 메뉴 상세 등록 (DTL)
    int insertMenuDtl(MenuDTO menuDTO);

    // 5. 메뉴 정보 수정 (MST)
    int updateMenuMst(MenuDTO menuDTO);

    // 6. 메뉴 상세 수정 (DTL)
    int updateMenuDtl(MenuDTO menuDTO);

    // 7. 메뉴 삭제 (MST 삭제 시 DTL은 Cascade 설정에 의해 자동 삭제됨)
    int deleteMenu(int menuIdx, String shopcode);

    // 8. 메뉴 중복 체크
    int checkMenuNameExists(@Param("shopCode") String shopCode, @Param("menuName") String menuName);
}