package eapli.base.jobapplicationmanagement.DTO;

import java.util.List;
import java.util.Set;

import eapli.framework.representations.dto.DTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@DTO
@Data
@AllArgsConstructor
public class RankingDTO {
    public RankingDTO() {
    }
    String jobOpening;
    Float extraVacancies;
    Set<RankOrderDTO> rankOrderList;
}
