package eapli.base.jobapplicationmanagement.domain;


import eapli.base.jobapplicationmanagement.DTO.RankOrderDTO;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@EqualsAndHashCode
public class RankOrder implements ValueObject, Serializable, DTOable<RankOrderDTO> {

    private static final long serialVersionUID = 1L;



    @OneToOne(optional = false)
    private JobApplication jobApplication;
    @Column(nullable = false)
    private OrderNumber number;


    public RankOrder(final JobApplication jobApplication, final OrderNumber number) {
        Preconditions.noneNull(jobApplication, number);
        this.jobApplication = jobApplication;
        this.number = number;
    }

    protected RankOrder() {
        // for ORM only
    }
    public RankOrderDTO toDTO() {
        return new RankOrderDTO(jobApplication.identity().identity(), number.toString());
    }

    public JobApplication jobApplication() {
        return this.jobApplication;
    }


    public OrderNumber number() {
        return this.number;
    }
}
