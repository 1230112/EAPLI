package eapli.base.app.backoffice.console.presentation.authz;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.jobmanagement.AddJobApplicationController;
import eapli.base.jobmanagement.JobService;
import eapli.base.usermanagement.application.AddUserController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AddJobApplicationUI extends AbstractUI {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final AddJobApplicationController theController = new AddJobApplicationController(authz, PersistenceContext.repositories().jobApplications(), new JobService());

    @Override
    protected boolean doShow() {
        try {

            final String candidateDataPath = Console.readLine("Candidate data filepath:");
            final String cvPath = Console.readLine("CV filepath:");

            File candidateData = new File(candidateDataPath);
            List<String> candidateDataList = readCandidateData(candidateData);

            List<String> ids = List.of(candidateDataList.get(0).split("-"));
            try {
                Integer.parseInt(ids.get(1));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid Job Application ID");
            }

            theController.addJobApplication(candidateDataList, ids, cvPath);

            System.out.println("Job Application added successfully!");
            return true;

        } catch (Exception e) {
            if (e.getMessage() != null) {
                System.out.println("Error: " + e.getMessage());
            } else {
                System.out.println("Error: " + e);
            }
            return false;
        }
    }

    public List<String> readCandidateData(File candidateData) {
        List<String> candidateDataList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(candidateData));
            String line;
            while ((line = br.readLine()) != null && line.length() > 0){
                candidateDataList.add(line);
            }
            br.close();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return candidateDataList;
    }

    @Override
    public String headline() { return "Add Job Application"; }
}
