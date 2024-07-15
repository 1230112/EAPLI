package eapli.base.app.common.console.presentation.candidate;


import eapli.base.candidatemanagement.DTO.ListCandidatesDTO;
import eapli.base.candidatemanagement.domain.Candidate;
import eapli.framework.visitor.Visitor;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
@SuppressWarnings({ "squid:S106" })
public class ListCandidatesPrinter implements Visitor<ListCandidatesDTO> {



    @Override
    public void visit(final ListCandidatesDTO visitee) {
        System.out.printf("%-40s%-40s%-40s%n", visitee.getEmail(), visitee.getName().firstName(), visitee.getName().lastName());
    }

}