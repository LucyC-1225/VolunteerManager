class Hour {
    private String date;
    private double hour; //individual hours
    private String description;

    public Hour(String date, double hour, String description) {
        this.date = date;
        this.hour = hour;
        this.description = description;
    }
    public String getDate() {
        return date;
    }

    public double getHour() {
        return hour;
    }

    public String getDescription() {
        return description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHour(double hour) {
        this.hour = hour;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDetails() {
        String str = "";
        str += "Date: " + date + "\n";
        str += "Hours: " + hour + "\n";
        str += "Description: " + description;
        return str;
    }
}