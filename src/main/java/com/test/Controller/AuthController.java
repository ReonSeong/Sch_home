package com.test.Controller;

import com.test.model.AuthDTO;
import com.test.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 로그인 처리
     * 세션 체크는 필요 없으며, 로그인 성공 시 세션에 정보를 담는 역할만 수행합니다.
     */
    @PostMapping("/login")
    public String handleLogin(
            @RequestParam("userId") String userId,
            @RequestParam("userPw") String userPw,
            HttpSession session) {

        // DAO 직접 생성 대신 서비스 호출
        AuthDTO user = authService.login(userId, userPw);

        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            return "redirect:/?error=invalid";
        }
    }

    /**
     * 로그아웃 처리
     * 인터셉터에서 이미 로그인 여부를 걸러주거나,
     * 설령 세션이 없더라도 invalidate()는 안전하므로 단순하게 유지합니다.
     */
    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate(); // 무조건 무효화
        return "redirect:/";
    }
}