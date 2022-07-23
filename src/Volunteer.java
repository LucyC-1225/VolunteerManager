public class Volunteer {
    private String name;
    private String email;
    private String role;
    private double hour;

    public Volunteer(String name, String email, String role, double hour) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.hour = hour;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public double getHour() {
        return hour;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setHour(double hour) {
        this.hour = hour;
    }
    public String getDetails() {
        String str = "";
        str += "Name: " + name + "\n";
        str += "Email: " + email + "\n";
        str += "Role: " + role + "\n";
        str += "Hours: " + hour + "\n";
        return str;
    }
}