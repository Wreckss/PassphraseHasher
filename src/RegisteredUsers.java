import java.util.ArrayList;

public class RegisteredUsers extends Utility {

    public ArrayList<User> allUsers = new ArrayList<>();

    public void createUser(String[] freshCredentials) {
        if (checkUsername(freshCredentials[0]) == -1) {
            User freshUser = new User(freshCredentials[0], freshCredentials[1]);
            System.out.printf("Adding %s...\n", freshUser.getName());
            allUsers.add(freshUser);
        } else {
            System.out.println("User already exists");
        }
    }

    private int checkUsername(String usernameAttempt) {
        int foundIndex = -1;
        for (int i = 0; i < allUsers.size(); i++) {
            if (usernameAttempt.equals(allUsers.get(i).getName())) {
                foundIndex = i;
                return foundIndex;
            }
        }
        return foundIndex;
    }

    public void printAllUsers() {
        if (allUsers.isEmpty()) {
            System.out.println("No users found");
        } else {
            for (int i = 0; i < allUsers.size(); i++) {
                System.out.printf("User #%s: %s\n", i + 1, allUsers.get(i).getName());
                System.out.printf("Password: %s\n\n", allUsers.get(i).getPass());
            }
        }
    }

    public void removeUser() {
        final String[] feedback = {
                "Select which user to remove:",
                "No users found"
        };
        if (!allUsers.isEmpty()) {
            System.out.println(feedback[0]);
            printAllUsers();
            int menuOption = menuValidation(stdIn.next(), allUsers.toArray());
            if (menuOption > 0 && menuOption <= allUsers.size()) {
                menuOption--;
                System.out.printf("Removing %s...\n", allUsers.get(menuOption).getName());
                allUsers.remove(menuOption);
            }
        } else {
            System.out.println(feedback[1]);
        }
    }

    public void logIn(String[] logInCredentials) {
        final int foundIndex = checkUsername(logInCredentials[0]);
        if (foundIndex != -1) {
            if (comparePassword(allUsers.get(foundIndex), logInCredentials[1])) {
                changePassword(allUsers.get(foundIndex));
            }
        }
    }

}
