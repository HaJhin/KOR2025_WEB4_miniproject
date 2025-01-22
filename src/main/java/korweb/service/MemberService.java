package korweb.service;

import jakarta.transaction.Transactional;
import korweb.model.dto.MemberDto;
import korweb.model.entity.MemberEntity;
import korweb.model.repository.MembeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (result) {return true;} else {return false;}

    } // f ed

} // c ed
