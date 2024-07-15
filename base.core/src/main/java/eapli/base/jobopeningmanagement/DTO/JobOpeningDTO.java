package eapli.base.jobopeningmanagement.DTO;

import eapli.base.jobopeningmanagement.domain.ContractType;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.jobopeningmanagement.domain.Mode;
import eapli.framework.representations.dto.DTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@DTO
@Data
@AllArgsConstructor
public class JobOpeningDTO {
    public JobOpeningDTO() {
    }

    private String jobReference;
    private String title;

    private String address;


    private String jobDescription;


    private String contractType;


    private int numberVacancies;


    private String mode;

    private String company;
    private String costumerManager;
}