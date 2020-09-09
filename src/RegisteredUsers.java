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
        if (checkSize()) {
            for (int i = 0; i < allUsers.size(); i++) {
                System.out.printf("User #%s: %s\n", i + 1, allUsers.get(i).getName());
                System.out.printf("Password: %s\n\n", allUsers.get(i).getPass());
            }
        }
    }

    public boolean checkSize() {
        if (allUsers.isEmpty()) {
            System.out.println("No users found");
            return false;
        } else {
            return true;
        }
    }

    public void removeUser() {
        if (checkSize()) {
            System.out.println("Select which user to remove:");
            printAllUsers();
            int menuOption = menuValidation(stdIn.next(), allUsers.toArray());
            if (menuOption > 0 && menuOption <= allUsers.size()) {
                menuOption--;
                System.out.printf("Removing %s...\n", allUsers.get(menuOption).getName());
                allUsers.remove(menuOption);
            }
        }
    }

    public void logIn(String[] logInCredentials) {
        if (checkSize()) {
            final int index = checkUsername(logInCredentials[0]);
            if (index != -1) {
                if (comparePassword(allUsers.get(index), logInCredentials[1])) {
                    changePassword(allUsers.get(index));
                }
            }
        }
    }

}
