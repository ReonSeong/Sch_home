/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-30
  Time: 오후 4:37
*/
package com.test.Controller;

import com.test.model.MenuDTO;
import com.test.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menu_manage")
    public String menuManagePage(Model model) {
        // 실제 운영 시에는 세션에서 shopCode를 가져와야 합니다.
        List<MenuDTO> menuList = menuService.getMenuList("MAT001");
        model.addAttribute("menus", menuList);
        return "menu_manage";
    }

    @GetMapping("/detail")
    @ResponseBody
    public MenuDTO getMenuDetail(@RequestParam("menuIdx") int menuIdx,
                                 @RequestParam("shopCode") String shopCode) {
        return menuService.getMenuDetail(menuIdx, shopCode);
    }

    @PostMapping("/save")
    @ResponseBody
    public String saveMenu(@ModelAttribute MenuDTO menuDTO,
                           @RequestParam(value="menuImage", required=false) MultipartFile menuImage,
                           HttpServletRequest request) {
        try {
            String uploadPath = request.getServletContext().getRealPath("/resources/images/Seoulgrade/");
            return menuService.registerMenu(menuDTO, menuImage, uploadPath);
        } catch (IllegalArgumentException e) {
            return "invalid_file";
        } catch (Exception e) {
            return "error";
        }
    }
}