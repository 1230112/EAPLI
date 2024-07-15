package eapli.base.app.common.console.presentation.jobOpening;

import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.framework.visitor.Visitor;

public class JobOpeningPrinter implements Visitor<JobOpening> {

    @Override
    public void visit(final JobOpening visitee) {
        System.out.printf("%-10s", visitee.identity());
    }
}
