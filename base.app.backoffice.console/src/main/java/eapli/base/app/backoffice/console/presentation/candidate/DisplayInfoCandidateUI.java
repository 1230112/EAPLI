package eapli.base.app.backoffice.console.presentation.candidate;

import eapli.base.candidatemanagement.DTO.CandidateDTO;
import eapli.base.candidatemanagement.application.CandidateService;
import eapli.base.candidatemanagement.domain.Candidate;

import eapli.base.candidatemanagement.application.DisplayInfoCandidateController;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;
import eapli.base.app.common.console.presentation.candidate.CandidatePrinter;

import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;

/**
 *
 * @author losa
 */
@SuppressWarnings({ "squid:S106" })
public class DisplayInfoCandidateUI extends AbstractListUI<CandidateDTO> {
    private DisplayInfoCandidateController theController = new DisplayInfoCandidateController(AuthzRegistry.authorizationService(), new CandidateService());

    @Override
    public String headline() {
        return "Display Info Candidate";
    }

    @Override
    protected String emptyMessage() {
        return "No data.";
    }

    @Override
    protected Iterable<CandidateDTO> elements() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the email of the candidate you want to search for:");
        String emailInput = scanner.nextLine();
        EmailAddress email = EmailAddress.valueOf(emailInput);
        Optional<CandidateDTO> candidateOptional = theController.findCandidateUserByEmail(email);
        return candidateOptional.map(Collections::singletonList).orElse(Collections.emptyList());
    }

    @Override
    protected Visitor<CandidateDTO> elementPrinter() {
        return new CandidatePrinter();
    }

    @Override
    protected String elementName() {
        return "User";
    }

    @Override
    protected String listHeader() {
        return String.format("%-30s%-30s%-30s%-30s%-30s%n", "EMAIL", "FIRST NAME", "LAST NAME", "REQUIREMENT", "PHONE");
    }
}
