package eapli.base.app.backoffice.console.presentation.rankcandidates;

import eapli.base.app.common.console.presentation.jobApplication.JobApplicationDTOPrinter;
import eapli.base.app.common.console.presentation.jobOpening.JobOpeningDTOPrinter;
import eapli.base.app.common.console.presentation.jobOpening.JobOpeningPrinter;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.jobapplicationmanagement.DTO.JobApplicationDTO;
import eapli.base.jobapplicationmanagement.DTO.RankOrderDTO;
import eapli.base.jobapplicationmanagement.application.RankedCandidatesController;
import eapli.base.jobapplicationmanagement.domain.JobApplicationId;
import eapli.base.jobapplicationmanagement.domain.OrderNumber;
import eapli.base.jobapplicationmanagement.domain.RankOrder;
import eapli.base.jobopeningmanagement.DTO.JobOpeningDTO;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RankCandidatesUI extends AbstractUI {

    // dependency injection - when constructing the object one must inject the dependencies to
    // infrastructure objects it needs. this should be handled by a DI/IoC container like Spring
    // Framework
    private final RankedCandidatesController theController = new RankedCandidatesController(
            AuthzRegistry.authorizationService(), PersistenceContext.repositories().rankings(), PersistenceContext.repositories().jobOpenings(), PersistenceContext.repositories().users(), PersistenceContext.repositories().jobApplications());

    @Override
    protected boolean doShow() {

        final Iterable<JobOpeningDTO> jobOpening = this.theController.getValidJobOpenings();
        if (!jobOpening.iterator().hasNext()) {
            System.out.println("You don't have any JobOpenings assigned. Nothing to rank.");
            return false;
        }
         SelectWidget<JobOpeningDTO> selector = new SelectWidget<JobOpeningDTO>("Job Openings:", jobOpening,
                new JobOpeningDTOPrinter());
        selector.show();
        final JobOpeningDTO theJobOpening = selector.selectedElement();
        final Iterable<JobApplicationDTO> jobApplication = this.theController.getValidJobApplications(theJobOpening);
        int count = 0;
        for (JobApplicationDTO application : jobApplication) {
            count++;
        }
        System.out.println("Number of JobApplications: " + count);
        System.out.println("Number of vacancies: " + theJobOpening.getNumberVacancies());
        System.out.println("............................................................");
        System.out.println("How many extra applications do you want to rank for the waitlist in relation with the number of vacancies?");
        System.out.println("Example: If you have 2 vacancies and you want to rank 2 extra applications for the waitlist, you should enter 1.");
        System.out.println("If you have 2 vacancies and you want to rank 4 extra applications for the waitlist, you should enter 2.");
        System.out.println("If you have 2 vacancies and you want to rank 1 extra applications for the waitlist, you should enter 0.5.");
        Float numberExtraApplications;
        Integer totalRankApplications;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Number of extra applications: ");
            numberExtraApplications = scanner.nextFloat();
            if (numberExtraApplications < 0 ||  (theJobOpening.getNumberVacancies() + numberExtraApplications)>count) {
                System.out.println("Please enter a positive natural integer and remember you can't rank a number bigger than the number of applications.");
            }
        } while (numberExtraApplications < 0 || (theJobOpening.getNumberVacancies() + numberExtraApplications)>count);
        numberExtraApplications = numberExtraApplications * (float)theJobOpening.getNumberVacancies();
        Integer numberExtraApplicationsaux = numberExtraApplications.intValue();

        totalRankApplications = theJobOpening.getNumberVacancies() + numberExtraApplicationsaux;



        final Set<RankOrderDTO> rankOrder = new HashSet<>();

        final Set<JobApplicationDTO> selectedApplications = new HashSet<>();
        for (int i = 1; i <= totalRankApplications; i++) {
            System.out.println("Enter JobApplicationId for RankOrder " + i + ": ");

            // Filter out the already selected applications
            Iterable<JobApplicationDTO> availableApplications = StreamSupport.stream(jobApplication.spliterator(), false)
                    .filter(jobApplicationDTO -> !selectedApplications.contains(jobApplicationDTO))
                    .collect(Collectors.toList());

            SelectWidget<JobApplicationDTO> selectorapli = new SelectWidget<>("Job Applications:", availableApplications,
                    new JobApplicationDTOPrinter());
            selectorapli.show();
            final JobApplicationDTO theJobApplication = selectorapli.selectedElement();

            // Add the selected application to the set of selected applications
            selectedApplications.add(theJobApplication);

            String numberAsString = String.valueOf(i);
            final RankOrderDTO newRankOrder = new RankOrderDTO(theJobApplication.getJobApplicationId(), numberAsString);
            rankOrder.add(newRankOrder);
        }

        try {
            this.theController.rankedCandidates(theJobOpening, numberExtraApplications, rankOrder);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("You tried to enter a ranking which already exists in the database.");
        }

        return false;
    }

    @Override
    public String headline() {
        return "Register Ranked Candidates";
    }
}