package korweb.model.entity;

import jakarta.persistence.*;
import korweb.model.dto.MemberDto;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 롬복
@Entity
@Table(name = "member")
@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class MemberEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;

    @Column(nullable = false , unique = true , length = 30)
    private String mid;

    @Column(nullable = false , length = 30)
    private String mpwd;

    @Column(nullable = false , length = 20)
    private String mname;

    @Column(nullable = false , unique = true , length = 50)
    private String memail;

    @Column(nullable = false , columnDefinition = "varchar(255)")
    private String mimg; // 회원프로필


    // toDto
    public MemberDto toDto() {
        return MemberDto.builder()
                .mno(this.mno)
                .mid(this.mid)
                .mpwd(this.mpwd)
                .mname(this.mname)
                .memail(this.memail)
                .mimg(this.mimg)
                .build();
    }
}
