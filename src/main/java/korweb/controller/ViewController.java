package korweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// === 템플릿을 반환하는 컨트롤러 클래스 === //

@Controller
public class ViewController {
    @GetMapping("/") // http://localhost:8080
    public String index() {
        return "index.html";
    } // f ed

    @GetMapping("/member/signup")
    public String signup() {
        return "/member/signup.html";
    } // f ed

    @GetMapping("/member/login")
    public String login() {
        return "/member/login.html";
    }

    @GetMapping("/member/mypage")
    public String myPage() {
        return "/member/mypage.html";
    }

    @GetMapping("/member/update")
    public String update() {
        return "/member/update.html";
    }
}
