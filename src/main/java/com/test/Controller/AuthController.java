package com.test.Controller;

import com.test.dao.AuthDAO;
import com.test.model.AuthDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth") // 클래스 레벨에 공통 경로 설정
public class AuthController {

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam("userId") String userId,
            @RequestParam("userPw") String userPw,
            HttpSession session) {

        AuthDAO DAO = new AuthDAO();
        AuthDTO user = DAO.loginCheck(userId, userPw);

        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/dashboard"; // 성공 시 대시보드로 이동
        } else {
            return "redirect:/?error=invalid"; // 실패 시 에러 파라미터와 함께 루트로 이동
        }
    }

    // [로그아웃 처리]
    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "redirect:/"; // 로그아웃 후 루트("/") 주소로 이동
    }
}