import java.util.ArrayList;

public class RegisteredUsers extends Utility {

    public ArrayList<User> allUsers = new ArrayList<>();

    public void createUser() {
        String creatingUser = collectInput(credentialOptions[4]);
        for (User allUser : allUsers) {
            if (creatingUser.equals(allUser.getName())) {
                System.out.println("User already exists");
                return;
            }
        }
        User user = new User(creatingUser,
                hashPassword(collectInput(credentialOptions[0])));
        System.out.printf("Adding %s...\n", user.getName());
        allUsers.add(user);
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
        String[] feedback = new String[]{"Select which user to remove:", "No users found"};
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

    public void logIn() {
        String[] feedback = {"Password incorrect", "User not found"};
        String[] logInCredentials = {collectInput(credentialOptions[4]),
                collectInput(credentialOptions[0])};

        int foundIndex = -1;
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getName().equals(logInCredentials[0])) {
                foundIndex = i;
            }
        }
        if (foundIndex > -1 && foundIndex < allUsers.size()) {
            if (checkPassword(allUsers.get(foundIndex), logInCredentials[1])) {
                changePassword(allUsers.get(foundIndex));
            } else {
                System.out.println(feedback[0]);
            }
        } else {
            System.out.println(feedback[1]);
        }
    }

}
