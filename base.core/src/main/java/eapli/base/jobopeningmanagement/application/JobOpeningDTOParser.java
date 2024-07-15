package eapli.base.jobopeningmanagement.application;

import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.DTO.JobOpeningDTO;
import eapli.base.jobopeningmanagement.domain.JobOpeningBuilder;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.jobopeningmanagement.repositories.JobOpeningRepository;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.money.domain.model.Money;
import eapli.framework.representations.dto.DTOParser;

public class JobOpeningDTOParser implements DTOParser<JobOpeningDTO, JobOpening> {

    private final UserRepository userRepository;
    private final AuthorizationService authz;

    private final JobOpeningRepository jobOpeningRepository;
    public JobOpeningDTOParser(final UserRepository userRepository, final AuthorizationService authz, final JobOpeningRepository jobOpeningRepository) {
        this.userRepository = userRepository;
        this.authz = authz;
        this.jobOpeningRepository = jobOpeningRepository;
    }

    @Override
    public JobOpening valueOf(final JobOpeningDTO dto) {
        //final SystemUser user = userRepository.ofIdentity(Username.valueOf(dto.getCostumerManager()))
          //      .orElseThrow(() -> new IllegalArgumentException("Unknown user: " + dto.getCostumerManager()));
        SystemUser user;
        try {

            user = authz.session().get().authenticatedUser();
        } catch (Exception e) {
            throw new IllegalStateException("Session has no authenticated user.", e);
        }
        // Print the username of the user
        System.out.println("Username: " + user.identity());
    JobOpening jobOpening = jobOpeningRepository.ofIdentity(JobReference.valueOf(dto.getJobReference())).orElseThrow(() -> new IllegalArgumentException("Unknown job reference: " + dto.getJobReference()));
        // build mandatory attributes
       ////       .withContractType(dto.getContractType()).withNumberVacancies(dto.getNumberVacancies()).withMode(dto.getMode()).withCompany(dto.getCompany()).withCostumerManager(user).build();
        // build it
        return jobOpening;
    }
}
