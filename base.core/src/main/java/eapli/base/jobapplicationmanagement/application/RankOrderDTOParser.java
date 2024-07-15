package eapli.base.jobapplicationmanagement.application;

import eapli.base.jobapplicationmanagement.DTO.RankOrderDTO;
import eapli.base.jobapplicationmanagement.DTO.RankingDTO;
import eapli.base.jobapplicationmanagement.domain.*;
import eapli.base.jobapplicationmanagement.repository.JobApplicationRepository;
import eapli.base.jobapplicationmanagement.repository.RankingRepository;
import eapli.base.jobopeningmanagement.DTO.JobOpeningDTO;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.jobopeningmanagement.repositories.JobOpeningRepository;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.representations.dto.DTOParser;

import java.util.HashSet;
import java.util.Set;

public class RankOrderDTOParser implements DTOParser<RankingDTO, Ranking> {


    private final AuthorizationService authz;

    private final JobApplicationRepository jobApplicationRepository;
    private final JobOpeningRepository jobOpeningRepository;
    public RankOrderDTOParser(final AuthorizationService authz, final JobApplicationRepository jobApplicationRepository, final JobOpeningRepository jobOpeningRepository) {

        this.authz = authz;
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobOpeningRepository = jobOpeningRepository;
    }

    @Override
    public Ranking valueOf(final RankingDTO dto) {

        JobOpening jobOpening = jobOpeningRepository.ofIdentity(JobReference.valueOf(dto.getJobOpening())).orElseThrow(() -> new IllegalArgumentException("Unknown job reference: " + dto.getJobOpening()));
        final Set<RankOrderDTO> rankOrderDto = dto.getRankOrderList();
        Set<RankOrder> rankOrderSet = new HashSet<>();
        for (RankOrderDTO rankOrderDTO : rankOrderDto) {
            JobApplicationId applicationId = JobApplicationId.valueOf(rankOrderDTO.getJobApplicationId());
            OrderNumber number = OrderNumber.valueOf(Integer.parseInt(rankOrderDTO.getNumber()));
            JobApplication jobApplication = jobApplicationRepository.ofIdentity(applicationId).orElseThrow(() -> new IllegalArgumentException("Unknown job application: " + applicationId));
            RankOrder rankOrderEntity = new RankOrder(jobApplication, number);

            rankOrderSet.add(rankOrderEntity);
        }
        final var builder = new RankingBuilder().withJobOpening(jobOpening).withExtraVacancies(dto.getExtraVacancies()).withRankOrder(rankOrderSet);

        return builder.build();
    }
}