package eapli.base.app.common.console.presentation.customer;

import eapli.framework.io.util.Console;

public class CustomerDataWidget {
    private String email;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String customerCode;
    private String companyName;
    private String companyAddress;

    public void show() {
        email = Console.readLine("E-Mail");
        firstName = Console.readLine("First Name");
        lastName = Console.readLine("Last Name");
        phoneNumber = Console.readLine("Phone Number");
        customerCode = Console.readLine("Customer Code");
        companyName = Console.readLine("Company Name");
        companyAddress = Console.readLine("Company Address");
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

    public String customerCode() {
        return customerCode;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public String companyName() { return companyName; }

    public String companyAddress() { return companyAddress; }
}
