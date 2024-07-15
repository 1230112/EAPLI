package eapli.base.jobapplicationmanagement.application;


import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobapplicationmanagement.repository.JobApplicationRepository;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.ApplicationService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;

import java.util.ArrayList;
import java.util.List;

@ApplicationService
public class RankingService {

    private final AuthorizationService authz;
    private final JobApplicationRepository jobApplicationRepository;

    public RankingService(final AuthorizationService authz, final JobApplicationRepository jobApplicationRepository) {
        // dependency injection - to make this object more testable we don't create the
        // infrastructure objects to avoid coupling to the implementation. This way, the controller
        // can be used in different scenarios with different implementations of the repository. for
        // instance, unit testing.
        this.authz = authz;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public void validateNumberRankedApplications(final JobOpening jobOpening, final Float extraVacancies) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,
                BaseRoles.CUSTOMER_MANAGER, BaseRoles.OPERATOR);

        final Iterable<JobApplication> jobApplications = this.jobApplicationRepository.findJobApplicationsAfterScreewningResultAndInterviewGradeIfItHasByJobReference(jobOpening);

        long count = jobApplications.spliterator().getExactSizeIfKnown();
        if (count == -1) {
            throw new IllegalStateException("Cannot determine the exact size of jobApplications");
        }
        if((jobOpening.numberVacancies() + extraVacancies) > count){
            throw new IllegalStateException("you don't have so many applications to rank");
        }
        if((extraVacancies*jobOpening.numberVacancies())%1 != 0){
            throw new IllegalStateException("you can't ");
        }

    }
}