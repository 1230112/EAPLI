package eapli.base.app.common.console.presentation.candidate;

import eapli.base.candidatemanagement.DTO.CandidateDTO;

import eapli.base.candidatemanagement.domain.Candidate;
import eapli.framework.visitor.Visitor;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
@SuppressWarnings({ "squid:S106" })
public class CandidatePrinter implements Visitor<CandidateDTO> {

    @Override
    public void visit(final CandidateDTO visitee) {
        System.out.printf("%-30s%-30s%-30s%-30s%-30s%n", visitee.getEmail(), visitee.getName().firstName(), visitee.getName().lastName(), visitee.getRequirementsFile(), visitee.getPhoneNumber());
            }


}