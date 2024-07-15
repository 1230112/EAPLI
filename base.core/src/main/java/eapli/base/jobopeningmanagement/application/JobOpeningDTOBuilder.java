package eapli.base.jobopeningmanagement.application;

import eapli.base.jobopeningmanagement.DTO.JobOpeningDTO;
import eapli.framework.money.domain.model.Money;
import eapli.framework.representations.RepresentationBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class JobOpeningDTOBuilder implements RepresentationBuilder<JobOpeningDTO> {

    private static final Logger LOGGER = LogManager.getLogger(JobOpeningDTOBuilder.class);

    private static final String PROPERTY_NOT_KNOW_IN_JOBOPENING_DTO = "Property '{}' not know in jobOpeningDTO";

    // an example of a builder that creates the "product" but hides that way from
    // the client code
    private JobOpeningDTO dto = new JobOpeningDTO();

    private String childObject = "";

    @Override
    public JobOpeningDTO build() {

        final JobOpeningDTO ret = dto;
        // ensure if someone reuses this builder won't change the already built object
        // but actually
        // work on a new object
        dto = new JobOpeningDTO();
        return ret;
    }

    @Override
    public RepresentationBuilder<JobOpeningDTO> startObject(final String name) {
        childObject = name;
        return this;
    }

    @Override
    public RepresentationBuilder<JobOpeningDTO> endObject() {
        childObject = "";
        return this;
    }

    @Override
    public RepresentationBuilder<JobOpeningDTO> withProperty(final String name, final String value) {
        if ("jobReference".equals(name)) {
            dto.setJobReference(value);
        }
        else if ("title".equals(name)) {
            dto.setTitle(value);
        }
     else if ("address".equals(name)) {
        dto.setAddress(value);
    }
        else if ("jobDescription".equals(name)) {
            dto.setJobDescription(value);
        }
        else if ("contractType".equals(name)) {
            dto.setContractType(value);
        }
        else if ("mode".equals(name)) {
            dto.setMode(value);
        }
        else if ("company".equals(name)) {
            dto.setCompany(value);
        }
         else {
            LOGGER.warn(PROPERTY_NOT_KNOW_IN_JOBOPENING_DTO, name);
        }
        return this;
    }

    @Override
    public RepresentationBuilder<JobOpeningDTO> withProperty(final String name, final Integer value) {
        if ("numberVacancies".equals(name)) {
            dto.setNumberVacancies(value);

        } else {
            LOGGER.warn(PROPERTY_NOT_KNOW_IN_JOBOPENING_DTO, name);
        }
        return this;
    }


    @Override
    public RepresentationBuilder<JobOpeningDTO> withElement(final String value) {
        LOGGER.warn("JobOpeningDTO has no collections; tried to create element {}", value);
        return this;
    }
}
