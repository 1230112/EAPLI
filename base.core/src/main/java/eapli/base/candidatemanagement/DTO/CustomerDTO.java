package eapli.base.candidatemanagement.DTO;

import eapli.base.candidatemanagement.domain.CustomerCode;
import eapli.base.candidatemanagement.domain.PhoneNumber;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.representations.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@DTO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Name name;
    private CustomerCode customerCode;
    private EmailAddress email;
    private PhoneNumber phoneNumber;
    private String companyName;
    private String companyAddress;
}
