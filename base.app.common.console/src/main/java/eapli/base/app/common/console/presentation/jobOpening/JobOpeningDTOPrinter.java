package eapli.base.app.common.console.presentation.jobOpening;

import eapli.base.jobopeningmanagement.DTO.JobOpeningDTO;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.framework.visitor.Visitor;

public class JobOpeningDTOPrinter implements Visitor<JobOpeningDTO> {

    @Override
    public void visit(final JobOpeningDTO visitee) {
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10d %-10s %-10s %-10s\n",
                visitee.getJobReference(),
                visitee.getTitle(),
                visitee.getAddress(),
                visitee.getJobDescription(),
                visitee.getContractType(),
                visitee.getNumberVacancies(),
                visitee.getMode(),
                visitee.getCompany(),
                visitee.getCostumerManager());
    }
}
