package korweb.controller;

import korweb.model.dto.MemberDto;
import korweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/member/signup.do")
    public boolean signup(@RequestBody MemberDto memberDto) {
        return memberService.signup(memberDto);
    }

    @PostMapping("/member/login.do")
    public boolean login(@RequestBody MemberDto memberDto) {
        return memberService.login(memberDto);
    }
}
