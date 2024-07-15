package eapli.base.app.common.console.presentation.jobApplication;

import eapli.base.jobapplicationmanagement.DTO.JobApplicationDTO;
import eapli.framework.visitor.Visitor;

public class JobApplicationDTOPrinter implements Visitor<JobApplicationDTO> {

    @Override
    public void visit(final JobApplicationDTO visitee) {
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",
                visitee.getJobApplicationId(),
                visitee.getJobOpening(),
                visitee.getCandidate(),
                visitee.getCv(),
                visitee.getScreeningResult(),
                visitee.getScreeningJustification(),
                visitee.getFinalResult());

    }
}
