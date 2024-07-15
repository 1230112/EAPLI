package eapli.base.jobapplicationmanagement.DTO;
import eapli.framework.representations.dto.DTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@DTO
@Data
@AllArgsConstructor
public class RankOrderDTO {
    public int jobApplicationId;
    public String number;
}
