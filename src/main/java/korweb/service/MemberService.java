package korweb.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import korweb.model.dto.MemberDto;
import korweb.model.dto.PointDto;
import korweb.model.entity.MemberEntity;
import korweb.model.entity.PointEntity;
import korweb.model.repository.MemberRepository;
import korweb.model.repository.PointRepository;
import org.apache.tomcat.util.net.jsse.PEMFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FileService fileService;

    @Transactional
    public boolean signup(MemberDto memberDto) {
        // - 프로필사진 첨부파일이 존재하면 업로드 진행
        if(memberDto.getUploadFile().isEmpty()) { // (1) 만약 업로드 파일이 비어 있으면
            memberDto.setMimg("default_profile.png");
        } // 디폴트 이미지 세팅
        else { // (2) 만약 업로드 파일이 존재하면 파일 서비스 객체 내 업로드 함수를 호출한다.
            String fileName = fileService.fileUpload(memberDto.getUploadFile()); // 업로드 함수에 multipart 객체 대입
            if (fileName == null) {return false;} // 업로드 실패 시 회원가입 실패
            else {memberDto.setMimg(fileName);}
            }

        // 1. 저장할 dto를 entity 타입으로 변환한다.
        MemberEntity memberEntity = memberDto.toEntity();
        // 2. 변환된 entity를 save 한다.
        MemberEntity saveEntity = memberRepository.save(memberEntity);
        // 3. save(영속성/연결된)한 엔티티를 반환한다.
        if(saveEntity.getMno() > 0) {
            PointDto pointDto = PointDto.builder()
                    .pcontent("회원가입 축하 포인트")
                    .pvalue(100)
                    .build();
            pointPayment(pointDto,memberEntity);
            return true;
        }
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
        boolean result = memberRepository.existsByMidAndMpwd(memberDto.getMid(), memberDto.getMpwd());
        if (result) {
            System.out.println("로그인 성공");
            setSession(memberDto.getMid());
            PointDto pointDto = PointDto.builder()
                    .pcontent("로그인 포인트")
                    .pvalue(1)
                    .build();
            MemberEntity memberEntity = memberRepository.findById(getMyInfo().getMno()).get();
            pointPayment(pointDto,memberEntity);
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
            MemberEntity memberEntity = memberRepository.findByMid(mid);
            return memberEntity.toDto();
        }
        return null;
    } // f ed

    // [7] 현재 로그인된 회원 탈퇴
    public boolean myDelete() {
        String mid = getSession();
        if(mid != null) {
            MemberEntity memberEntity = memberRepository.findByMid(mid);
            memberRepository.delete(memberEntity);
            return true;
        }
        return false;
    } // f ed

    // [8] 현재 로그인된 회원 정보 수정 , mname 닉네임 , memail 이메일
    @Transactional
    public boolean myUpdate(MemberDto memberDto) {
        String mid = getSession();
        if (mid != null) {
            MemberEntity memberEntity = memberRepository.findByMid(mid);
            memberEntity.setMname(memberDto.getMname());
            memberEntity.setMemail(memberDto.getMemail());
            return true;
        }
        return false;
    } // f ed

    @Autowired private PointRepository pointRepository;

    @Transactional
    // [9] 포인트 지급 함수 . 지급내용은 pcontent , 지급수량은 pvalue
    public boolean pointPayment(PointDto pointDto , MemberEntity memberEntity) {
        PointEntity pointEntity = pointDto.toEntity();
        pointEntity.setMemberEntity(memberEntity);

        PointEntity saveEntity = pointRepository.save(pointEntity);
        if(saveEntity.getPno() > 0){return true;} return false;
    }

    // [10] 현재 내 포인트 조회
    public PointDto myPointList() {
        String mid = getSession();
        if (mid != null) {
            MemberEntity memberEntity = memberRepository.findByMid(mid);
            PointEntity pointEntity = pointRepository.findById(memberEntity.getMno()).get();
            return pointEntity.toDto();
        }
        return null;
    }
} // c ed
