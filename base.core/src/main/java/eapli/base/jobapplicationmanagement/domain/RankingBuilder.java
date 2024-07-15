package eapli.base.jobapplicationmanagement.domain;


import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.framework.domain.model.DomainFactory;

import java.util.Set;


public class RankingBuilder implements DomainFactory<Ranking> {
    private Ranking ranking;
    //private JobReference jobReference;
    private JobOpening jobOpening;
    private ExtraVacancies extraVacancies;
    private Integer count=0;


    public RankingBuilder withJobOpening(final JobOpening jobOpening) {
        this.jobOpening = jobOpening;
        return this;
    }
    public RankingBuilder withExtraVacancies(final ExtraVacancies extraVacancies) {
        this.extraVacancies = extraVacancies;
        return this;
    }
    public RankingBuilder withExtraVacancies(final Float extraVacancies) {
        this.extraVacancies = ExtraVacancies.valueOf(extraVacancies);
        return this;
    }
    private Ranking buildOrThrow() {
        if (ranking != null) {
            return ranking;
        }
        if (jobOpening != null ) {
            ranking = new Ranking(jobOpening, extraVacancies);
            return ranking;
        } else {
            throw new IllegalStateException();
        }
    }
    public RankingBuilder withRankOrder(final Set<RankOrder> rankOrders) {
        // we will simply ignore if we receive a null set
        if (rankOrders != null) {
            rankOrders.forEach(this::withRankOrders);
            count++;
        }
        return this;
    }


    public RankingBuilder withRankOrders(final RankOrder rankOrder) {
        buildOrThrow();
        ranking.addRankOrder(rankOrder);
        return this;
    }

    public void validateNumberRankedApplications() {

        if((this.jobOpening.numberVacancies() + this.extraVacancies.toFloat()) > count){
            throw new IllegalStateException("you don't have so many applications to rank");
        }
        if((this.extraVacancies.toFloat()*this.jobOpening.numberVacancies())%1 != 0){
            throw new IllegalStateException("you can't ");
        }
    }

    @Override
    public Ranking build() {
        final Ranking ret = buildOrThrow();
        // make sure we will create a new instance if someone reuses this builder and do
        // not change
        // the previously built dish.

        ranking = null;
        validateNumberRankedApplications();
        return ret;
    }
}
