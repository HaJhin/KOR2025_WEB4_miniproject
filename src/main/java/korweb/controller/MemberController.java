package korweb.controller;

import korweb.model.dto.MemberDto;
import korweb.model.dto.PointDto;
import korweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    // 1. 회원가입 HTTP 매핑
    @PostMapping("/member/signup.do")
    public boolean signup(@RequestBody MemberDto memberDto) {
        return memberService.signup(memberDto);
    }

    // 2. 로그인 HTTP 매핑
    @PostMapping("/member/login.do")
    public boolean login(@RequestBody MemberDto memberDto) {
        return memberService.login(memberDto);
    }

    // 3. 현재 로그인된 회원 아이디 http 매핑
    @GetMapping("/member/login/id.do")
    public String loginId() {
        return memberService.getSession();
    } // f ed

    // 4. 현재 로그인된 회원 로그아웃
    @GetMapping("/member/logout.do")
    public boolean logout() {
        return memberService.deleteSession();
    } // f ed

    // 5. 현재 로그인된 회원 정보 확인
    @GetMapping("/member/myinfo.do")
    public MemberDto getMyInfo() {
        return memberService.getMyInfo();
    } // f ed

    // 6. 현재 로그인된 회원 탈퇴
    @DeleteMapping("/member/delete.do")
    public boolean myDelete() {
        return memberService.myDelete();
    } // f ed

    // 7. 현재 로그인된 회원 정보 수정
    @PutMapping("/member/update.do")
    public boolean myUpdate(@RequestBody MemberDto memberDto) {
        return memberService.myUpdate(memberDto);
    }

    // 8. 내 포인트 지급 내역 조회
    @GetMapping("/member/pointlist.do")
    public PointDto myPointList() {
        return memberService.myPointList();
    }
}
