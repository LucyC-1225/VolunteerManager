import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class VolunteerManagement
{
    private JFrame frame;
    private JPanel homePanel;
    private JLabel totalHours;
    private DefaultListModel hourData;
    private JList hourList;
    private JButton addHours; //date, hours, description
    private JButton viewHourDetails;
    private JButton editHours;
    private JButton deleteHours;
    private JButton saveAndLogout;
    private VolunteerUser user;

    public VolunteerManagement(VolunteerUser volunteerUser)
    {
        this.user = volunteerUser;
    }
    public void initialize()
    {
        frame = new JFrame();
        frame.setSize(400,500);
        homePanel = new JPanel();
        homePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        totalHours = new JLabel("Total hours: " + user.calculateTotalHours() + " hours");
        homePanel.add(totalHours, c);

        c.gridy = 1;
        hourData = new DefaultListModel();
        for (int i = 0; i < user.getHours().size(); i++)
        {
            hourData.addElement((i + 1) + ". " + user.getHours().get(i).getDate() + " | " + user.getHours().get(i).getHour() + " hours");
        }
        hourList = new JList(hourData);
        hourList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        hourList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(hourList);
        listScroller.setPreferredSize(new Dimension(250, 150));
        homePanel.add(listScroller, c);

        c.gridy = 2;
        addHours = new JButton("Add Hours");
        addHours.addActionListener(this::actionPerformed);
        homePanel.add(addHours, c);

        c.gridy = 3;
        viewHourDetails = new JButton("Show Hour Details");
        viewHourDetails.addActionListener(this::actionPerformed);
        homePanel.add(viewHourDetails, c);

        c.gridy = 4;
        editHours = new JButton("Edit Hour Details");
        editHours.addActionListener(this::actionPerformed);
        homePanel.add(editHours, c);

        c.gridy = 5;
        deleteHours = new JButton("Remove Hours");
        deleteHours.addActionListener(this::actionPerformed);
        homePanel.add(deleteHours, c);

        c.gridy = 6;
        saveAndLogout = new JButton("Save and Logout");
        saveAndLogout.addActionListener(this::actionPerformed);
        homePanel.add(saveAndLogout, c);

        frame.add(homePanel);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == addHours) {
            String date = JOptionPane.showInputDialog(frame, "Enter the date:");
            double hours = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter hours for that date:"));
            String description = JOptionPane.showInputDialog(frame, "Enter the description of what you did:");
            Hour newHour = new Hour(date, hours, description);
            user.getHours().add(newHour);
            JOptionPane.showMessageDialog(frame, "Sucessfully logged hour");
            frame.setVisible(false);
            initialize();
        }
        if (source == viewHourDetails) {
            int index = hourList.getSelectedIndex();
            JOptionPane.showMessageDialog(frame, user.getHours().get(index).getDetails());
        }
        if (source == editHours) {
            int index = hourList.getSelectedIndex();
            int editField = Integer.parseInt(JOptionPane.showInputDialog(frame, "What do you want to edit?\n1. Date\n2. Hours\n3. Description"));
            if (editField == 1) {
                String date = JOptionPane.showInputDialog(frame, "Enter the date:");
                user.getHours().get(index).setDate(date);
                hourData.setElementAt((index + 1) + ". " + user.getHours().get(index).getDate() + " | " + user.getHours().get(index).getHour() + " hours", index);
            }
            if (editField == 2) {
                double hour = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter the Volunteer Hour:"));
                user.getHours().get(index).setHour(hour);
                hourData.setElementAt((index + 1) + ". " + user.getHours().get(index).getDate() + " | " + user.getHours().get(index).getHour() + " hours", index);
            }
            if (editField == 3) {
                String description = JOptionPane.showInputDialog(frame, "Enter the Description:");
                user.getHours().get(index).setDescription(description);
            }
            JOptionPane.showMessageDialog(frame, "Sucessfully Edited Hour");
        }
        if (source == deleteHours) {
            int index = hourList.getSelectedIndex();
            user.getHours().remove(index);
            frame.setVisible(false);
            initialize();
        }
        if (source == saveAndLogout) {
            save(user);
            save();
            JOptionPane.showMessageDialog(frame, "Saving...Saved!");
            System.exit(0);
        }
    }
    public void save(VolunteerUser user) {
        try {
            FileOutputStream fos = new FileOutputStream("src/" + user.getUsername() + ".txt");
            String username = user.getUsername() + "\n";
            fos.write(username.getBytes());

            String password = user.getPassword() + "\n";
            fos.write(password.getBytes());

            for (int i = 0; i < user.getHours().size(); i++) {
                String date = user.getHours().get(i).getDate();
                double hours = user.getHours().get(i).getHour();
                String description = user.getHours().get(i).getDescription();
                String hourInfo = date + "|" + hours + "|" + description + "\n";
                fos.write(hourInfo.getBytes());
            }
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void save() {
        FileOutputStream f = null;
        try {
            f = new FileOutputStream("src/allUsernames.txt", true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < AccountManager.getAllVolunteerUsers().size(); i++){
            String name = AccountManager.getAllVolunteerUsers().get(i).getUsername() + "\n";
            try {
                String str = "";
                f.write(str.getBytes());
                f.write(name.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void retrieve() {
        File file = new File("src/allUsernames.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //retrieves all admin usernames ever created
        ArrayList<String> usernames = new ArrayList<String>();
        while (sc.hasNextLine()){
            String str = sc.nextLine();
            if (str.indexOf("admin") == -1) {
                usernames.add(str);
            }
        }
        //for every username, there is an account
        for (int i = 0; i < usernames.size(); i++) {
            String username = usernames.get(i);
            File f = new File("src/" + username + ".txt");
            Scanner sc2 = null;
            try {
                sc2 = new Scanner(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            sc2.nextLine();
            String password = sc2.nextLine();
            //creating user
            VolunteerUser user = new VolunteerUser(username, password);
            AccountManager.getAllVolunteerUsers().add(user);
            //for every user account, there is a list of volunteers
            while (sc2.hasNextLine()) {
                String hourInfo = sc2.nextLine();
                String hourDate = hourInfo.substring(0, hourInfo.indexOf("|"));
                hourInfo = hourInfo.substring(hourInfo.indexOf("|") + 1);
                double hourHour = Double.parseDouble(hourInfo.substring(0, hourInfo.indexOf("|")));
                hourInfo = hourInfo.substring(hourInfo.indexOf("|") + 1);
                String hourDescription = hourInfo;
                //creating Hour
                Hour hour = new Hour(hourDate, hourHour, hourDescription);
                user.getHours().add(hour);
            }
        }
    }
}