package eapli.base.interviewmanagement.DTO;

import eapli.base.jobapplicationmanagement.DTO.RankOrderDTO;
import eapli.framework.representations.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.time.Duration;
import java.util.Calendar;
import java.util.Set;

@DTO
@Data
@AllArgsConstructor
public class InterviewApplicationDTO {
    public InterviewApplicationDTO() {
    }
    int jobApplication;
    Calendar dateTime;
    String timeSpent;
    String answersFile;
    Integer grade;


}