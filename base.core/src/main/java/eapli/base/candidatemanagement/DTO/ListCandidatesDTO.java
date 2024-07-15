package eapli.base.candidatemanagement.DTO;

import eapli.base.candidatemanagement.domain.RequirementsFile;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.base.candidatemanagement.domain.PhoneNumber;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.representations.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@DTO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCandidatesDTO {
    private EmailAddress email;
    private Name name;

}