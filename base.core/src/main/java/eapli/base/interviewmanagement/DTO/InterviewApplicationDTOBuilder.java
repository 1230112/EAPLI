package eapli.base.interviewmanagement.DTO;

import eapli.framework.representations.RepresentationBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.Duration;
import java.util.Calendar;

public class InterviewApplicationDTOBuilder implements RepresentationBuilder<InterviewApplicationDTO> {

    private static final Logger LOGGER = LogManager.getLogger(InterviewApplicationDTOBuilder.class);

    private static final String PROPERTY_NOT_KNOW_IN_INTERVIEWAPPLICATION_DTO = "Property '{}' not know in interviewApplicationDTO";

    // an example of a builder that creates the "product" but hides that way from
    // the client code
    private InterviewApplicationDTO dto = new InterviewApplicationDTO();

    private String childObject = "";

    @Override
    public InterviewApplicationDTO build() {

        final InterviewApplicationDTO ret = dto;
        // ensure if someone reuses this builder won't change the already built object
        // but actually
        // work on a new object
        dto = new InterviewApplicationDTO();
        return ret;
    }

    @Override
    public RepresentationBuilder<InterviewApplicationDTO> startObject(final String name) {
        childObject = name;
        return this;
    }

    @Override
    public RepresentationBuilder<InterviewApplicationDTO> endObject() {
        childObject = "";
        return this;
    }

    @Override
    public RepresentationBuilder<InterviewApplicationDTO> withProperty(final String name, final String value) {
        if ("timeSpent".equals(name)) {
            dto.setTimeSpent(value);

        }
        else if ("answersFile".equals(name)) {
            dto.setAnswersFile(value);

        }
        else {
            LOGGER.warn(PROPERTY_NOT_KNOW_IN_INTERVIEWAPPLICATION_DTO, name);
        }
        return this;
    }

    @Override
    public RepresentationBuilder<InterviewApplicationDTO> withProperty(final String name, final Integer value) {
        if ("grade".equals(name)) {
            dto.setGrade(value);

        } else if ("jobApplication".equals(name)) {
            dto.setJobApplication(value);
        } else {
            LOGGER.warn(PROPERTY_NOT_KNOW_IN_INTERVIEWAPPLICATION_DTO, name);
        }
        return this;
    }

    @Override
    public RepresentationBuilder<InterviewApplicationDTO> withProperty(final String name, final Calendar value) {
        if ("dateTime".equals(name)) {
            dto.setDateTime(value);

        } else {
            LOGGER.warn(PROPERTY_NOT_KNOW_IN_INTERVIEWAPPLICATION_DTO, name);
        }
        return this;
    }


    @Override
    public RepresentationBuilder<InterviewApplicationDTO> withElement(final String value) {
        LOGGER.warn("InterviewApplicationDTO has no collections; tried to create element {}", value);
        return this;
    }
}