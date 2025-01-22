package korweb.model.dto;

import korweb.model.entity.MemberEntity;
import lombok.*;

@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class MemberDto {
    private int mno;
    private String mid;
    private String mpwd;
    private String mname;
    private String memail;

    // dto -> entity 로 변환하는 toEntity 함수
    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .mno(this.mno)
                .mid(this.mid)
                .mpwd(this.mpwd)
                .mname(this.mname)
                .memail(this.memail)
                .build();
    }
}
