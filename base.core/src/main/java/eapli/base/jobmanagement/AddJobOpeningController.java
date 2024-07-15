package eapli.base.jobmanagement;

import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobOpeningBuilder;
import eapli.base.jobopeningmanagement.repositories.JobOpeningRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddJobOpeningController {

    private final AuthorizationService authz;
    private final JobOpeningRepository jobOpeningRepository;

    public AddJobOpeningController(AuthorizationService authz, JobOpeningRepository jobOpeningRepository) {
        this.authz = authz;
        this.jobOpeningRepository = jobOpeningRepository;
    }

    public JobOpening addJobOpening(final String title, final String address, final String jobDescription,
                                    final String contractType, final int numberVacancies, final String mode, final String company) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER);

        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int millisecond = calendar.get(Calendar.MILLISECOND);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        String jobReference = String.format("%s-%d%d%d", company, month, day, millisecond);


        SystemUser thisUser;
        try {
            thisUser = authz.session().get().authenticatedUser();
        } catch (Exception e) {
            throw new IllegalStateException("Session has no authenticated user.", e);
        }
        JobOpening jo = new JobOpeningBuilder().withJobReference(jobReference).withTitle(title).withAddress(address).withJobDescription(jobDescription)
                .withContractType(contractType).withNumberVacancies(numberVacancies).withMode(mode).withCompany(company).withCostumerManager(thisUser).build();
        return jobOpeningRepository.save(jo);
    }

    public JobOpening addJobOpening(final String jobReference, final String title, final String address, final String jobDescription,
                                    final String contractType, final int numberVacancies, final String mode, final String company, final SystemUser user) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER);
        JobOpening jo = new JobOpeningBuilder().withJobReference(jobReference).withTitle(title).withAddress(address).withJobDescription(jobDescription)
                .withContractType(contractType).withNumberVacancies(numberVacancies).withMode(mode).withCompany(company).withCostumerManager(user).build();
        return jobOpeningRepository.save(jo);
    }
}
