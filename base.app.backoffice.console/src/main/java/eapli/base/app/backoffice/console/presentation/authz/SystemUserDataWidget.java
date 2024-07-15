package eapli.base.app.backoffice.console.presentation.authz;


import eapli.framework.io.util.Console;

public class SystemUserDataWidget {
    private String email;

    private String firstName;
    private String lastName;


    public void show() {
        email = Console.readLine("E-Mail");

        firstName = Console.readLine("First Name");
        lastName = Console.readLine("Last Name");

    }

    public String email() {
        return this.email;
    }



    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }


}

