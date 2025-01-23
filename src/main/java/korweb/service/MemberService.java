package korweb.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import korweb.model.dto.MemberDto;
import korweb.model.entity.MemberEntity;
import korweb.model.repository.MembeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Objects;

@Service
public class MemberService {
    @Autowired
    private MembeRepository membeRepository;

    public boolean signup(MemberDto memberDto) {
        // 1. 저장할 dto를 entity 타입으로 변환한다.
        MemberEntity memberEntity = memberDto.toEntity();
        // 2. 변환된 entity를 save 한다.
        MemberEntity saveEntity = membeRepository.save(memberEntity);
        // 3. save(영속성/연결된)한 엔티티를 반환한다.
        if(saveEntity.getMno() > 0) {return true;}
        return false;
    }

    @Transactional
    public boolean login(MemberDto memberDto) {
        // [방법1]
        /*
        // 1. 모든 회원 엔티티를 조회한다.
        List<MemberEntity> memberEntityList = membeRepository.findAll();
        // 2. 모든 회원 엔티티를 하나씩 조회한다.
        for(int i = 0; i < memberEntityList.size(); i++) {
            // 3. i번째의 엔티티 꺼내기
            MemberEntity memberEntity = memberEntityList.get(i);
            // 4. i번째의 엔티티 아이디가 입력받은(dto) 아이디와 같으면
            if (memberEntity.getMid().equals(memberDto.getMid())) {
                // 5. i번째의 엔티티 아이디가 입력받은(dto) 비밀번호와 같으면
                if (memberEntity.getMpwd().equals(memberDto.getMpwd())) {
                    System.out.println("로그인 성공");
                    return true;
                } // if ed
            } // if ed
        } // for ed
`       */

        // [방법2]
        boolean result = membeRepository.existsByMidAndMpwd(memberDto.getMid(), memberDto.getMpwd());
        if (result) {
            System.out.println("로그인 성공");
            setSession(memberDto.getMid());
            return true;
        } else {
            System.out.println("로그인 실패");
            return false;
        }

    } // f ed

    // ====== 세션 관련 함수 ====== //
    // (1) 내장된 톰캣 서버의 요청 객체
    @Autowired private HttpServletRequest request;

    // [3] 세션객체에 로그인된 회원 아이디를 추가하는 함수
    public boolean setSession(String mid) {
        // (2) 요청 객체를 이용한 톰캣 내 세션 객체를 반환한다.
        HttpSession httpSession = request.getSession();

        // (3) 세션 객체에 속성(새로운 값) 추가한다.
        httpSession.setAttribute("loginId",mid);
        return true;
    } // f ed

    // [4] 세션객체에 로그인된 회원아이디를 반환하는 함수
    public String getSession() {
        // (2)
        HttpSession httpSession = request.getSession();
        // (4) 세션 객체에 속성명의 값 반환한다. * 반환 타입이 object 이다.
        Object object = httpSession.getAttribute("loginId");
        // (5) 검사 후 타입 변환
        if (object != null) {
            // 만약 세션 정보가 존재하면
            String mid = (String)object;
            return mid;
        }
        return null;
    } // f ed

    // [5] 세션 객체 내 정보 초기화 : 로그아웃
    public boolean deleteSession() {
        HttpSession httpSession = request.getSession(); // (2)
        httpSession.removeAttribute("loginId"); // 세션에 저장된 객체 삭제
        return true;
    }

    // [6] 현재 로그인된 회원의 정보 조회
    public MemberDto getMyInfo() {
        // 1. 현재 세션에 저장된 회원 아이디 조회
        String mid = getSession();
        // 2. 만약 로그인 상태이면
        if (mid != null) {
            // 3. 회원아이디로 엔티티 조회
            MemberEntity memberEntity = membeRepository.findByMid(mid);
            return memberEntity.toDto();
        }
        return null;
    } // f ed

    // [7] 현재 로그인된 회원 탈퇴
    public boolean myDelete() {
        String mid = getSession();
        if(mid != null) {
            MemberEntity memberEntity = membeRepository.findByMid(mid);
            membeRepository.delete(memberEntity);
            return true;
        }
        return false;
    } // f ed

    // [8] 현재 로그인된 회원 정보 수정 , mname 닉네임 , memail 이메일
    @Transactional
    public boolean myUpdate(MemberDto memberDto) {
        String mid = getSession();
        if (mid != null) {
            MemberEntity memberEntity = membeRepository.findByMid(mid);
            memberEntity.setMname(memberDto.getMname());
            memberEntity.setMemail(memberDto.getMemail());
            return true;
        }
        return false;
    } // f ed

} // c ed
