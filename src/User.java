import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private ArrayList<Volunteer> volunteers;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        volunteers = new ArrayList<Volunteer>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVolunteers(ArrayList<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }
}