package eapli.base.interviewmanagement.application;

import eapli.base.interviewmanagement.DTO.InterviewApplicationDTOBuilder;
import eapli.base.jobapplicationmanagement.DTO.JobApplicationDTO;
import eapli.base.jobapplicationmanagement.DTO.RankOrderDTO;
import eapli.base.interviewmanagement.DTO.InterviewApplicationDTO;
import eapli.base.jobapplicationmanagement.application.JobApplicationDTOService;
import eapli.base.interviewmanagement.application.InterviewApplicationDTOParser;
import eapli.base.interviewmanagement.domain.InterviewApplication;
import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.base.jobapplicationmanagement.repository.JobApplicationRepository;
import eapli.base.interviewmanagement.repository.InterviewApplicationRepository;
import eapli.base.jobopeningmanagement.DTO.JobOpeningDTO;
import eapli.base.jobopeningmanagement.application.JobOpeningDTOParser;
import eapli.base.jobopeningmanagement.application.JobOpeningDTOService;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.jobopeningmanagement.repositories.JobOpeningRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

import java.util.Calendar;
import java.util.Set;

@UseCaseController
public class  ScheaduleInterviewController {

    private final AuthorizationService authz;
    private final InterviewApplicationRepository interviewApplicationRepository;
    private final JobOpeningDTOService svcJobOpenings;
    private final JobOpeningDTOParser jobOpeningParser;
    private final JobApplicationDTOService svcJobApplications;
    private final InterviewApplicationDTOParser interviewApplicationParser;
    private  final JobApplicationRepository jobApplicationRepository;
    private final InterviewApplicationService interviewApplicationService;



    public ScheaduleInterviewController(final AuthorizationService authz, final InterviewApplicationRepository repository, final JobOpeningRepository jobOpeningRepository, final UserRepository userRepository, final JobApplicationRepository jobApplicationRepository) {
        this.authz = authz;
        this.interviewApplicationRepository = repository;
        this.svcJobOpenings = new JobOpeningDTOService(authz, jobOpeningRepository);
        this.jobOpeningParser = new JobOpeningDTOParser(userRepository, authz, jobOpeningRepository);
        this.svcJobApplications = new JobApplicationDTOService(authz, jobApplicationRepository);
        this.interviewApplicationParser = new InterviewApplicationDTOParser(authz, jobApplicationRepository, jobOpeningRepository);
        this.jobApplicationRepository = jobApplicationRepository;
        this.interviewApplicationService = new InterviewApplicationService(authz, repository);
    }

    public InterviewApplication scheaduleInterview(final Calendar dateTime, JobApplicationDTO jobApplicationDTO) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER);

        InterviewApplicationDTO interviewApplicationDTO = new InterviewApplicationDTOBuilder().withProperty("jobApplication", jobApplicationDTO.getJobApplicationId()).withProperty("dateTime", dateTime).build();

        InterviewApplication interviewApplication = interviewApplicationParser.valueOf(interviewApplicationDTO);
        interviewApplicationService.validateSameDateTime();
        return interviewApplicationRepository.save(interviewApplication);
    }

    public Iterable<JobOpeningDTO> listMyJobOpeningsForScheaduleInterview() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER);
        SystemUser currentUser =  authz.session().get().authenticatedUser();
        return svcJobOpenings.listJobOpeningsWithInterviewAssignedToMeInScreeningOrInterviewPhase(currentUser);
    }
    public Iterable<JobApplicationDTO> listAcceptedApplicationsForInterview(final JobOpeningDTO jobOpening) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER);
        final JobOpening jobOpeningEntity = jobOpeningParser.valueOf(jobOpening);
        return svcJobApplications.listJobApplicationsAfterScreewningResultByJobReference(jobOpeningEntity);
    }
}
