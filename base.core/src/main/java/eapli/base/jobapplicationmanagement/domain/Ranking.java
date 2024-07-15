package eapli.base.jobapplicationmanagement.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import eapli.base.jobapplicationmanagement.DTO.RankOrderDTO;
import eapli.base.jobapplicationmanagement.DTO.RankingDTO;
import eapli.base.jobopeningmanagement.domain.JobOpening;
import eapli.base.jobopeningmanagement.domain.JobReference;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import java.util.*;

@Entity
public class Ranking implements AggregateRoot<JobReference>, DTOable<RankingDTO> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;
    @Id
    @OneToOne(optional = false)
    private JobOpening jobOpening;

    @Column(nullable = false)
    private ExtraVacancies extraVacancies;
    @JsonProperty
    @ElementCollection
    private final Set<RankOrder> rankOrderList = new HashSet<>();




    public Ranking(final JobOpening jobOpening, final ExtraVacancies extraVacancies) {

        Preconditions.noneNull(jobOpening);

        this.jobOpening = jobOpening;
        this.extraVacancies = extraVacancies;
       // validateNumberRankedApplications();
    }

    protected Ranking() {
        // for ORM only
    }



    public JobOpening jobOpening() {
        return this.jobOpening;
    }

public ExtraVacancies extraVacancies() {
        return this.extraVacancies;
    }


    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean sameAs(final Object other) {
        if (!(other instanceof eapli.base.candidatemanagement.domain.Candidate))
            return false;
        if (this == other)
            return true;


        final eapli.base.jobapplicationmanagement.domain.Ranking that = (eapli.base.jobapplicationmanagement.domain.Ranking) other;

        return this.jobOpening.sameAs(that.jobOpening()) && this.extraVacancies.equals(that.extraVacancies());
    }

    public boolean addRankOrder(final RankOrder rankOrder) {
        return rankOrderList.add(new RankOrder(rankOrder.jobApplication(), rankOrder.number()));

    }

    public RankingDTO toDTO() {
        Set<RankOrderDTO> rankOrderDTOList = new HashSet<>();
        for (RankOrder rankOrder : this.rankOrderList) {
            rankOrderDTOList.add(rankOrder.toDTO());
        }
        return new RankingDTO(this.jobOpening.identity().toString(), this.extraVacancies.toFloat(), rankOrderDTOList);
    }
    public Set<RankOrder> rankOrders() {
        // notice the unmodifiable "copy" we are returning to keep with the Information
        // Expert principle and disallow callers to change "our" attributes
        return Collections.unmodifiableSet(rankOrderList);
    }
    @Override
    public JobReference identity() {
        return this.jobOpening.jobReference();
    }

}