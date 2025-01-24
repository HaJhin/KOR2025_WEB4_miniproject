package korweb.model.entity;

import jakarta.persistence.*;
import korweb.model.dto.PointDto;
import lombok.*;

@Entity
@Table(name = "point")
@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class PointEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pno;

    @Column(nullable = false)
    private int pvalue;

    @Column(nullable = false)
    private String pcontent;

    @ManyToOne
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity;

    public PointDto toDto() {
        return PointDto.builder()
                .pno(this.pno)
                .pvalue(this.pvalue)
                .pcontent(this.pcontent)
                .build();
    }
}
