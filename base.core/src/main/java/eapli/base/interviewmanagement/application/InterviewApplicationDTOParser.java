package eapli.base.interviewmanagement.application;

import eapli.base.interviewmanagement.DTO.InterviewApplicationDTO;
import eapli.base.interviewmanagement.domain.InterviewApplication;

import eapli.base.interviewmanagement.domain.InterviewApplicationBuilder;
import eapli.base.jobapplicationmanagement.domain.JobApplication;
import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.base.jobapplicationmanagement.repository.JobApplicationRepository;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.base.jobopeningmanagement.repositories.JobOpeningRepository;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.representations.dto.DTOParser;

import java.util.HashSet;
import java.util.Set;

public class InterviewApplicationDTOParser implements DTOParser<InterviewApplicationDTO, InterviewApplication> {


    private final AuthorizationService authz;

    private final JobApplicationRepository jobApplicationRepository;
    private final JobOpeningRepository jobOpeningRepository;
    public InterviewApplicationDTOParser(final AuthorizationService authz, final JobApplicationRepository jobApplicationRepository, final JobOpeningRepository jobOpeningRepository) {

        this.authz = authz;
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobOpeningRepository = jobOpeningRepository;
    }

    @Override
    public InterviewApplication valueOf(final InterviewApplicationDTO dto) {

        JobApplication jobApplication = jobApplicationRepository.ofIdentity(JobApplicationId.valueOf(dto.getJobApplication())).orElseThrow(() -> new IllegalArgumentException("Unknown job reference: " + dto.getJobApplication()));

        final var builder = new InterviewApplicationBuilder().withJobApplication(jobApplication).withDateTime(dto.getDateTime()).withTimeSpent(null).withAnswersFile(null).withGrade(0);


        return builder.build();
    }
}