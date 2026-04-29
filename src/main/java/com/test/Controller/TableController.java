/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-29
  Time: 오후 8:27
*/

package com.test.Controller;

import com.test.DAO.TableDAO;
import com.test.Model.AuthDTO;
import com.test.Model.TableDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TableController {

    /**
     * 1. 테이블 관리 페이지 진입 (GET)
     * URL: http://localhost:8081/table_status
     */
    @GetMapping("/table_status")
    public String getTableList(HttpSession session, Model model) {

        // 세션에서 유저 정보 확인
        AuthDTO user = (AuthDTO) session.getAttribute("user");

        if (user == null) {
            return "redirect:/"; // 세션 없으면 로그인 페이지로 리다이렉트
        }

        // DB에서 해당 매장의 테이블 리스트 조회
        TableDAO dao = new TableDAO();
        List<TableDTO> tableList = dao.getTableList(user.getShopCode());

        // JSP로 데이터 전달
        model.addAttribute("tableList", tableList);

        return "table_status"; // table_status.jsp 호출
    }

    /**
     * 2. 테이블 최종 상태 일괄 동기화 (POST)
     * 사용자가 편집 모드에서 추가, 삭제, 이동을 마친 후 '변경사항 저장'을 눌렀을 때 호출됩니다.
     * AJAX 요청 URL: /table/saveLayout
     */
    @PostMapping("/table/saveLayout")
    @ResponseBody
    public String saveLayout(@RequestBody List<TableDTO> layoutData, HttpSession session) {

        // 1. 보안 체크: 세션 유효성 확인
        AuthDTO user = (AuthDTO) session.getAttribute("user");
        if (user == null) {
            return "fail: unauthorized";
        }

        try {
            // 2. 데이터 전처리: 전달받은 모든 테이블 리스트에 현재 매장의 SHOP_CODE 주입
            for (TableDTO dto : layoutData) {
                dto.setShopCode(user.getShopCode());
            }

            // 3. DAO 호출: 기존 데이터를 정리하고 전달받은 리스트로 새로 동기화
            // (내부적으로 전체 삭제 후 일괄 삽입 또는 차집합 삭제/Upsert 로직 수행)
            TableDAO dao = new TableDAO();
            boolean result = dao.syncTableLayout(user.getShopCode(), layoutData);

            if (result) {
                System.out.println("매장 [" + user.getShopCode() + "] 테이블 배치 동기화 성공 (" + layoutData.size() + "개)");
                return "success";
            } else {
                return "fail";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "error: " + e.getMessage();
        }
    }
}