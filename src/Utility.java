import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Utility {

    public final Scanner stdIn = new Scanner(System.in);
    public final String[] credentialOptions = {
            "Enter your password:",
            "Enter your old password:",
            "Enter your new password:",
            "Confirm your new password:",
            "Enter your username:"
    };

    public String collectInput(String filler) {
        String input;
        do {
            System.out.printf("%s\n", filler);
            input = stdIn.next();
        } while (checkStringLength(input));
        return input;
    }

    private boolean checkStringLength(String unverified) {
        if (unverified.length() < 4 || unverified.length() > 12) {
            System.out.println("Submissions must be 4-12 characters in length.");
            return true;
        }
        return false;
    }

    public boolean comparePassword(User user, String attempt) {
        if (user.getPass().equals(attempt)) {
            return true;
        } else {
            System.out.println("Password incorrect");
            return false;
        }
    }

    public void changePassword(User user) {
        final String[] newPasswords = new String[2];
        final String[] feedbackMessages = {
                "Cannot reuse passwords",
                "Passwords do not match",
                "Password updated successfully"
        };
        for (int i = 0; i < newPasswords.length; i++) {
            //add hashed input to array
            newPasswords[i] = hashPassword(collectInput(credentialOptions[i + 2]));
            //check if input matches original password
            if (newPasswords[i].equals(user.getPass())) {
                System.out.println(feedbackMessages[i]);
                return;
            }
        }
        //check if input was used as a password in the past
        for (int i = 0; i < user.getUsedPasswords().size(); i++) {
            if (newPasswords[i].equals(user.getUsedPasswords().get(i))) {
                System.out.println(feedbackMessages[0]);
                return;
            }
        }
        //if first input matches second, set as new password
        if (newPasswords[0].equals(newPasswords[1])) {
            user.setPass(newPasswords[1]);
            System.out.println(feedbackMessages[2]);
        } else { //otherwise deny reset
            System.out.println(feedbackMessages[1]);
        }
    }

    public String hashPassword(String rawInput) {
        final String[] feedback = {
                "SHA-256",
                "%064x",
                "Failed to hash password",
                "Failed",
                ""          //used to hold the hashed output before return
        };
        try {
            MessageDigest md = MessageDigest.getInstance(feedback[0]);
            md.update(rawInput.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            //format hashed output, add to placeholder index
            feedback[4] = String.format(feedback[1], new BigInteger(1, digest));
        } catch (NoSuchAlgorithmException ae) {
            System.out.println(feedback[2]);
            feedback[4] = feedback[3];
        }
        return feedback[4];
    }

    public void menu() {
        final RegisteredUsers users = new RegisteredUsers();
        boolean quit = false;
        final String[] menuOptions = {
                "Create new user",
                "Print user credentials",
                "Remove a user",
                "Reset password",
                "Exit"
        };
        //user created for testing
//        User demoUser = new User("xxxx", hashPassword("ffff"));
//        users.allUsers.add(demoUser);
        do {
            for (int i = 0; i < menuOptions.length; i++) {
                System.out.printf("%s. %s\n", i + 1, menuOptions[i]);
            }
            int menuOption = menuValidation(stdIn.next(), menuOptions);
            switch (menuOption) {
                case 1:
                    users.createUser(gatherCredentials());
                    break;
                case 2:
                    users.printAllUsers();
                    break;
                case 3:
                    users.removeUser();
                    break;
                case 4:
                    if (users.checkSize()) {
                        users.logIn(gatherCredentials());
                    }
                    break;
                case 5:
                    quit = exit();
                    break;
                default:
                    //menuValidation() makes default unnecessary
            }
        } while (!quit);
    }

    private String[] gatherCredentials() {
        return new String[] {
                collectInput(credentialOptions[4]),                   //login username attempt
                hashPassword(collectInput(credentialOptions[0]))      //login password attempt
        };
    }

    private boolean exit() {
        System.out.println("Exiting...");
        return true;
    }

    public int menuValidation(Object unverified, Object[] menu) {
        int menuOption = 0;
        try {
            menuOption = Integer.parseInt(unverified.toString());
            if (menuOption <= 0 || menuOption > menu.length) {
                System.out.printf("Submissions must be 1-%s\n", menu.length);
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Must submit a numerical value.");
        }
        return menuOption;
    }

}
