package eapli.base.app.backoffice.console.presentation.candidate;


import eapli.base.app.common.console.presentation.candidate.ListCandidatesPrinter;
import eapli.base.candidatemanagement.DTO.ListCandidatesDTO;
import eapli.base.candidatemanagement.application.CandidateService;
import eapli.base.candidatemanagement.application.ListCandidatesController;

import eapli.base.candidatemanagement.domain.Candidate;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.visitor.Visitor;

import java.util.*;

/**
 *
 * @author losa
 */
@SuppressWarnings({ "squid:S106" })
public class ListCandidatesUI extends AbstractListUI<ListCandidatesDTO> {
    private ListCandidatesController theController = new ListCandidatesController(AuthzRegistry.authorizationService(), new CandidateService());

    @Override
    public String headline() {
        return "Display Info Candidate";
    }

    @Override
    protected String emptyMessage() {
        return "No data.";
    }

    @Override
    protected Iterable<ListCandidatesDTO> elements() {
        return theController.getAll();
            }

    @Override
    protected Visitor<ListCandidatesDTO> elementPrinter() {
        return new ListCandidatesPrinter();
    }

    @Override
    protected String elementName() {
        return "User";
    }

    @Override
    protected String listHeader() {
        return String.format("%-40s%-40s%-40s%n", "EMAIL", "FIRST NAME", "LAST NAME");
    }
}
