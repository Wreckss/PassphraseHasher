import java.util.ArrayList;

public class User {

    private String name;
    private String pass;
    private ArrayList<String> usedPasswords = new ArrayList<>();

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
        usedPasswords.add(pass);
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
        usedPasswords.add(pass);
    }

    public ArrayList<String> getUsedPasswords() {
        return usedPasswords;
    }
}
