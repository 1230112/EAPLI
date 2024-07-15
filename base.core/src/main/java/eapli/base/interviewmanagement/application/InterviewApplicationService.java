package eapli.base.interviewmanagement.application;

import eapli.base.interviewmanagement.domain.DateTime;
import eapli.base.interviewmanagement.domain.InterviewApplication;
import eapli.base.interviewmanagement.repository.InterviewApplicationRepository;
import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.base.jobapplicationmanagement.repository.JobApplicationRepository;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.repositories.JobOpeningRepository;
import eapli.framework.application.ApplicationService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.Calendar;
import java.util.List;

@ApplicationService
public class InterviewApplicationService {

    private final AuthorizationService authz;
    private final InterviewApplicationRepository interviewApplicationRepository;


    public InterviewApplicationService(final AuthorizationService authz, final InterviewApplicationRepository interviewApplicationRepository) {
        // dependency injection - to make this object more testable we don't create the
        // infrastructure objects to avoid coupling to the implementation. This way, the controller
        // can be used in different scenarios with different implementations of the repository. for
        // instance, unit testing.
        this.authz = authz;
        this.interviewApplicationRepository = interviewApplicationRepository;

    }
    public void validateSameDateTime() {

        SystemUser currentUser = authz.session().get().authenticatedUser();
        Iterable<InterviewApplication> interviews = interviewApplicationRepository.findInterviewApplicationsByCustomerManager(currentUser);
        for (InterviewApplication interview : interviews) {
            DateTime interviewDate = interview.dateTime();
            for (InterviewApplication interview2 : interviews) {
                if (interview != interview2) {
                    DateTime interviewDate2 = interview2.dateTime();
                    if (interviewDate.equals(interviewDate2)) {
                        System.out.println("Interviews at the same time. You will need to find another interviewer to be able to do them");
                    }
                }
            }
        }
    }
}