import java.util.ArrayList;

class VolunteerUser {
    private String username;
    private String password;
    private ArrayList<Hour> hours;

    public VolunteerUser(String username, String password) {
        this.username = username;
        this.password = password;
        hours = new ArrayList<Hour>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Hour> getHours() {
        return hours;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHours(ArrayList<Hour> hours) {
        this.hours = hours;
    }
    public double calculateTotalHours() {
        double total = 0;
        for (int i = 0; i < hours.size(); i++) {
            total += hours.get(i).getHour();
        }
        return total;
    }
}