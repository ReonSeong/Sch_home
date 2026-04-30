/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-30
  Time: 오후 4:37
*/

package com.test.Controller;

import com.test.dao.MenuDAO;
import com.test.model.MenuDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class MenuController {

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuDAO menuDAO;

    /**
     * 메뉴 관리 메인 페이지
     * DB에서 전체 메뉴 목록을 가져와서 JSP로 전달합니다.
     */
    @GetMapping("/menu_manage")
    public String menuManagePage(Model model) {
        try {
            // 매장코드 MAT001에 해당하는 전체 메뉴 리스트 조회
            String shopcode = "MAT001";
            List<MenuDTO> menuList = menuDAO.selectAllMenuList(shopcode);

            // JSP에서 ${menus}로 접근할 수 있도록 Model에 담기
            model.addAttribute("menus", menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "menu_manage";
    }

    /**
     * 메뉴 상세 정보 가져오기 (AJAX용)
     * 특정 메뉴 클릭 시 성분 정보 등을 포함한 상세 데이터를 반환합니다.
     */
    @GetMapping("/detail")
    @ResponseBody
    public MenuDTO getMenuDetail(@RequestParam("menuIdx") int menuIdx,
                                 @RequestParam("shopCode") String shopCode) {

        // DAO 호출 시 변수명 확인
        return menuDAO.selectMenuDetail(menuIdx, shopCode);
    }

    /**
     * 메뉴 등록 프로세스 (AJAX용)
     * MST와 DTL 테이블에 각각 데이터를 저장합니다.
     */
    @PostMapping("/save")
    @ResponseBody
    public String saveMenu(@ModelAttribute MenuDTO menuDTO,
                           @RequestParam(value="menuImage", required=false) MultipartFile menuImage,
                           jakarta.servlet.http.HttpServletRequest request) {

        logger.info("======= [AJAX SAVE MENU] 시작 =======");

        try {
            // 1. 중복 체크
            int existingCount = menuDAO.checkMenuNameExists(menuDTO.getShopCode(), menuDTO.getMenuName());
            if (existingCount > 0) {
                logger.info("중복 메뉴 발생: {}", menuDTO.getMenuName());
                return "exists";
            }

            // 2. 파일 업로드 처리 (이미지가 있을 때만 실행)
            if (menuImage != null && !menuImage.isEmpty()) {
                try {
                    String uploadPath = request.getServletContext().getRealPath("/resources/upload/menu/");
                    // [주의] 여기서 에러가 나면 전체가 실패함.
                    // avif 에러가 걱정된다면 일단 아래처럼 일반 저장으로 테스트 권장
                    String savedFileName = com.test.util.FileUpload.saveAsWebp(menuImage, uploadPath);
                    menuDTO.setImagePath("resources/upload/menu/" + savedFileName);
                } catch (Exception imgExt) {
                    logger.error("이미지 변환 중 에러 발생(하지만 DB 저장은 계속 진행): ", imgExt);
                    // 이미지 변환 실패 시 이미지 경로 없이 저장하고 싶다면 여기서 return 하지 않음
                }
            } else {
                logger.info("이미지 없음: 기본 경로 혹은 null 처리");
                menuDTO.setImagePath(null); // 혹은 기본 이미지 경로
            }

            // 3. 기본값 설정
            if (menuDTO.getRegUser() == null) menuDTO.setRegUser("admin");
            if (menuDTO.getCurrency() == null) menuDTO.setCurrency("RSD");

            // 4. DB 저장
            menuDAO.insertMenuMst(menuDTO);
            menuDAO.insertMenuDtl(menuDTO);

            logger.info("메뉴 등록 완료: {}", menuDTO.getMenuName());
            return "success";

        } catch (Exception e) {
            logger.error("전체 저장 프로세스 중 에러 발생: ", e);
            return "error";
        }
    }
}