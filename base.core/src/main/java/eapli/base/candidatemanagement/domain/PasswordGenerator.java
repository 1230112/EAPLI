package eapli.base.candidatemanagement.domain;

import eapli.framework.infrastructure.authz.application.PasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.Password;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

public class PasswordGenerator {

    private static final String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lowerCaseLetters = upperCaseLetters.toLowerCase();
    private static final String numbers = "0123456789";
    private static final String specialCharacters = "!@#$%^&*()";
    private static final String allCharacters = upperCaseLetters + lowerCaseLetters + numbers + specialCharacters;


    public PasswordGenerator() {
    }


    /**
     * This method should return a random password, with 8 to 12 characters, containing at
     * least one uppercase letter, one lowercase letter, one number and one special character.
     *
     * @return a random password as String
     */
    public static String generate(final PasswordPolicy policy, final PasswordEncoder encoder) {

        Random random = new Random();
        StringBuilder password;

        boolean encodedAndValid = false;

        do {
            password = new StringBuilder();
            for (int i = 0; i < 8 + random.nextInt(5); i++) {
                password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
            }

            try {
                Password.encodedAndValid(password.toString(), policy, encoder)
                        .orElseThrow(IllegalArgumentException::new);
                encodedAndValid = true;
            } catch (IllegalArgumentException e) {
                encodedAndValid = false;
            }

        } while (!password.toString().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[" + specialCharacters + "]).+$") || !encodedAndValid);

        return password.toString();
    }


    public static String generateWithoutPolicyAndEncoder() {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = upperCaseLetters.toLowerCase();
        String numbers = "0123456789";
        String specialCharacters = "!@#$%^&*()";
        String allCharacters = upperCaseLetters + lowerCaseLetters + numbers + specialCharacters;

        Random random = new Random();
        StringBuilder password;

        do {
            password = new StringBuilder();
            for (int i = 0; i < 8 + random.nextInt(5); i++) {
                password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
            }
        } while (!password.toString().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[" + specialCharacters + "]).+$"));

        return password.toString();
    }
}
