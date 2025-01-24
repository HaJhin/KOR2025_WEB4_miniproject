package korweb.model.dto;

import korweb.model.entity.PointEntity;
import lombok.*;

@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class PointDto {
    private int pno;
    private int pvalue;
    private String pcontent;

    public PointEntity toEntity() {
        return PointEntity.builder()
                .pno(this.pno)
                .pvalue(this.pvalue)
                .pcontent(this.pcontent)
                .build();
    }
}
