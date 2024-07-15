package eapli.base.jobapplicationmanagement.application;


import java.util.Set;


import eapli.base.jobapplicationmanagement.DTO.JobApplicationDTO;
import eapli.base.jobapplicationmanagement.DTO.RankOrderDTO;
import eapli.base.jobapplicationmanagement.DTO.RankingDTO;
import eapli.base.jobapplicationmanagement.domain.*;
import eapli.base.jobapplicationmanagement.repository.JobApplicationRepository;
import eapli.base.jobapplicationmanagement.repository.RankingRepository;
import eapli.base.jobopeningmanagement.DTO.JobOpeningDTO;
import eapli.base.jobopeningmanagement.application.JobOpeningDTOParser;
import eapli.base.jobopeningmanagement.application.JobOpeningDTOService;
import eapli.base.jobopeningmanagement.application.ListJobOpeningService;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.repositories.JobOpeningRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

@UseCaseController
public class  RankedCandidatesController {

    private final AuthorizationService authz;
    private final RankingRepository rankingRepository;
    private final JobOpeningDTOService svcJobOpenings;
    private final JobOpeningDTOParser jobOpeningParser;
    private final JobApplicationDTOService svcJobApplications;
    private final RankOrderDTOParser rankOrderParser;
    private final RankingService rankingService;


    public RankedCandidatesController(final AuthorizationService authz, final RankingRepository repository, final JobOpeningRepository jobOpeningRepository, final UserRepository userRepository, final JobApplicationRepository jobApplicationRepository){
        this.authz = authz;
        this.rankingRepository = repository;
        this.svcJobOpenings = new JobOpeningDTOService(authz, jobOpeningRepository);
        this.jobOpeningParser = new JobOpeningDTOParser(userRepository, authz, jobOpeningRepository);
        this.svcJobApplications = new JobApplicationDTOService(authz, jobApplicationRepository);
        this.rankOrderParser = new RankOrderDTOParser(authz, jobApplicationRepository, jobOpeningRepository);
        this.rankingService = new RankingService(authz, jobApplicationRepository);
    }

    public Ranking rankedCandidates(final JobOpeningDTO jobOpening, final Float extraVacancies, final Set<RankOrderDTO> rankOrderDto) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER);

        RankingDTO rankingDTO = new RankingDTO(jobOpening.getJobReference(), extraVacancies, rankOrderDto);
        Ranking ranking = rankOrderParser.valueOf(rankingDTO);
        rankingService.validateNumberRankedApplications(jobOpeningParser.valueOf(jobOpening), extraVacancies);

        return rankingRepository.save(ranking);
    }

    public Iterable<JobOpeningDTO> getValidJobOpenings() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER);
        return svcJobOpenings.allJobOpeningsAssignedToMeAnalysisPhase();
    }
    public Iterable<JobApplicationDTO> getValidJobApplications(final JobOpeningDTO jobOpening) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER);
        final JobOpening jobOpeningEntity = jobOpeningParser.valueOf(jobOpening);
        return svcJobApplications.allJobApplicationsAfterScreewningResultByJobReference(jobOpeningEntity);
    }
}