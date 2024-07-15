package eapli.base.jobapplicationmanagement.DTO;

import eapli.framework.representations.dto.DTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@DTO
@Data
@AllArgsConstructor
public class JobApplicationDTO {
    public JobApplicationDTO() {
    }
    int jobApplicationId;
    String jobOpening;
    String candidate;
    String cv;
    String screeningResult;
    String screeningJustification;
    String finalResult;

}
