/**
 *  File Name : TableController.java
 *  Writer : ReonQ
 *  Updated Date     Version     User        Change log
 *  2026-04-19           0.1     ReonQ       First make
 *  2026-05-01           0.2     ReonQ       add Call DB to Check menuList
 *  Now Version : 0.2
 *  Description:
 *  TableController.java
 */

package com.test.Controller;

import com.test.dao.MenuDAO;
import com.test.model.AuthDTO;
import com.test.model.MenuDTO;
import com.test.model.TableDTO;
import com.test.service.TableService; // 서비스 임포트
import com.test.util.JsonParser;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class TableController {

    @Autowired
    private TableService tableService;

    @Autowired
    private MenuDAO menuDAO;

    //1. 테이블 관리 페이지 진입
    @GetMapping("/table_status")
    public String getTableList(HttpSession session, Model model) {
        AuthDTO user = (AuthDTO) session.getAttribute("user");
        if (user == null) return "redirect:/";

        String shopCode = user.getShopCode();

        // 1. 테이블 리스트 조회
        List<TableDTO> tableList = tableService.getTableList(shopCode);
        model.addAttribute("tableList", tableList);

        // 2. 메뉴 리스트 조회 및 JSON 변환
        List<MenuDTO> menuList = menuDAO.selectAllMenuList(shopCode);
        String menuListJson = JsonParser.toJson(menuList);
        model.addAttribute("menuListJson", menuListJson);

        return "table_status";
    }


    //2. 테이블 배치 정보 동기화 (AJAX)
    @PostMapping("/table/saveLayout")
    @ResponseBody
    public String saveLayout(@RequestBody List<TableDTO> layoutData, HttpSession session) {
        AuthDTO user = (AuthDTO) session.getAttribute("user");
        if (user == null) return "fail: unauthorized";

        boolean result = tableService.syncTableLayout(user.getShopCode(), layoutData);

        return result ? "success" : "fail";
    }
}