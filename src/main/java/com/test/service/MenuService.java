/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-05-01
  Time: 오후 5:42
*/

package com.test.service;

import com.test.dao.MenuDAO;
import com.test.model.MenuDTO;
import com.test.util.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuDAO menuDAO;

    /**
     * 전체 메뉴 목록 조회
     */
    public List<MenuDTO> getMenuList(String shopCode) {
        return menuDAO.selectAllMenuList(shopCode);
    }

    /**
     * 메뉴 상세 조회
     */
    public MenuDTO getMenuDetail(int menuIdx, String shopCode) {
        return menuDAO.selectMenuDetail(menuIdx, shopCode);
    }

    /**
     * 메뉴 저장 (파일 업로드 + DB 저장)
     */
    @Transactional(rollbackFor = Exception.class)
    public String registerMenu(MenuDTO menuDTO, MultipartFile menuImage, String uploadPath) throws Exception {

        // 1. 중복 체크
        int existingCount = menuDAO.checkMenuNameExists(menuDTO.getShopCode(), menuDTO.getMenuName());
        if (existingCount > 0) return "exists";

        // 2. 파일 업로드 처리
        if (menuImage != null && !menuImage.isEmpty()) {
            // FileUpload 유틸리티 호출 (이미 여기서 jpg/png 체크)
            String savedFileName = FileUpload.saveFile(menuImage, uploadPath);
            menuDTO.setImagePath("resources/images/Seoulgrade/" + savedFileName);
        }

        // 3. 기본값 설정
        if (menuDTO.getRegUser() == null) menuDTO.setRegUser("admin");
        if (menuDTO.getCurrency() == null) menuDTO.setCurrency("RSD");

        // 4. DB 저장 (MyBatis useGeneratedKeys를 통해 menuIdx가 자동 세팅됨)
        menuDAO.insertMenuMst(menuDTO);
        menuDAO.insertMenuDtl(menuDTO);

        return "success";
    }
}