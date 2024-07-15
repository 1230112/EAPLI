package eapli.base.app.common.console.presentation.candidate;


import eapli.framework.io.util.Console;

/**
 * widget for reading user data Jorge Santos ajs@isp.ipp.pt
 */
public class CandidateDataWidget {
    private String email;

    private String firstName;
    private String lastName;
    private String requirementsFile;
    private String phoneNumber;

    public void show() {
        email = Console.readLine("E-Mail");
        firstName = Console.readLine("First Name");
        lastName = Console.readLine("Last Name");

        requirementsFile = Console.readLine("RequirementsFile (Optional, it can be empty)");
        phoneNumber = Console.readLine("Phone Number");
    }





    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public String email() {
        return this.email;
    }

    public String requirementsFile() {
        return requirementsFile;
    }
    public String phoneNumber() {
        return phoneNumber;
    }
}
