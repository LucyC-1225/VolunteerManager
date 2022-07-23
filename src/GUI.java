import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class GUI {
    private User user;
    private JFrame frame;
    //home panel
    private JPanel homePanel;
    private DefaultListModel volunteerData;
    private JList volunteerList;
    private JButton addVolunteer;
    private JButton showVolunteerDetail;
    private JButton sendHourVerificationEmail;
    private JButton editVolunteerDetail;
    private JButton removeVolunteer;
    private JButton saveAndLogOut;

    public GUI(User user) {
        this.user = user;
    }
    public void initialize() {
        frame = new JFrame("Account Frame");
        frame.setSize(400,500);
        homePanel = new JPanel();
        homePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        volunteerData = new DefaultListModel();
        for (int i = 0; i < user.getVolunteers().size(); i++) {
            volunteerData.addElement((i + 1) + ". " + user.getVolunteers().get(i).getName() + " | " + user.getVolunteers().get(i).getHour() + " hours");
        }
        volunteerList = new JList(volunteerData);
        volunteerList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        volunteerList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(volunteerList);
        listScroller.setPreferredSize(new Dimension(250, 150));
        homePanel.add(listScroller, c);

        c.gridy = 1;
        addVolunteer = new JButton("Add Volunteer");
        addVolunteer.addActionListener(this::actionPerformed);
        homePanel.add(addVolunteer, c);

        c.gridy = 2;
        showVolunteerDetail = new JButton("Show Volunteer Details");
        showVolunteerDetail.addActionListener(this::actionPerformed);
        homePanel.add(showVolunteerDetail, c);

        c.gridy = 3;
        sendHourVerificationEmail = new JButton("Send Verification Email");
        sendHourVerificationEmail.addActionListener(this::actionPerformed);
        homePanel.add( sendHourVerificationEmail, c);

        c.gridy = 4;
        editVolunteerDetail = new JButton("Edit Volunteer Details");
        editVolunteerDetail.addActionListener(this::actionPerformed);
        homePanel.add(editVolunteerDetail, c);

        c.gridy = 5;
        removeVolunteer = new JButton("Remove Volunteer");
        removeVolunteer.addActionListener(this::actionPerformed);
        homePanel.add(removeVolunteer, c);

        c.gridy = 6;
        saveAndLogOut = new JButton("Save and Logout");
        saveAndLogOut.addActionListener(this::actionPerformed);
        homePanel.add(saveAndLogOut, c);

        frame.add(homePanel);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    //Action Listener
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == addVolunteer) {
            String name = JOptionPane.showInputDialog(frame, "Enter the Volunteer Name:");
            String email = JOptionPane.showInputDialog(frame, "Enter the Volunteer Email:");
            String role = JOptionPane.showInputDialog(frame, "Enter the Volunteer Role:");
            double hour = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter the Volunteer Hours:"));
            Volunteer newVolunteer = new Volunteer(name, email, role, hour);
            user.getVolunteers().add(newVolunteer);
            JOptionPane.showMessageDialog(frame, "Sucessfully added volunteer");
            frame.setVisible(false);
            initialize();
        }
        if (source == showVolunteerDetail) {
            int index = volunteerList.getSelectedIndex();
            JOptionPane.showMessageDialog(frame, user.getVolunteers().get(index).getDetails());
        }
        if (source == sendHourVerificationEmail) {
            //NEED TO BE COMPLETED
        }
        if (source == editVolunteerDetail) {
            int index = volunteerList.getSelectedIndex();
            int editField = Integer.parseInt(JOptionPane.showInputDialog(frame, "What do you want to edit?\n1. Name\n2. Email\n3. Role\n4. Hour"));
            if (editField == 1) {
                String name = JOptionPane.showInputDialog(frame, "Enter the Volunteer Name:");
                user.getVolunteers().get(index).setName(name);
                volunteerData.setElementAt((index + 1) + ". " + user.getVolunteers().get(index).getName() + " | " + user.getVolunteers().get(index).getHour() + " hours", index);
            }
            if (editField == 2) {
                String email = JOptionPane.showInputDialog(frame, "Enter the Volunteer Email:");
                user.getVolunteers().get(index).setEmail(email);
            }
            if (editField == 3) {
                String role = JOptionPane.showInputDialog(frame, "Enter the Volunteer Role:");
                user.getVolunteers().get(index).setRole(role);
            }
            if (editField == 4) {
                double hour = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter the Volunteer Hours:"));
                user.getVolunteers().get(index).setHour(hour);
                volunteerData.setElementAt((index + 1) + ". " + user.getVolunteers().get(index).getName() + " | " + user.getVolunteers().get(index).getHour() + " hours", index);
            }
            JOptionPane.showMessageDialog(frame, "Sucessfully Edited Volunteer");
        }
        if (source == removeVolunteer) {
            int index = volunteerList.getSelectedIndex();
            user.getVolunteers().remove(index);
            frame.setVisible(false);
            initialize();
        }
        if (source == saveAndLogOut) {
            save();
            save(user);
            JOptionPane.showMessageDialog(frame, "Saving...Saved!");
            System.exit(0);
        }
    }
    public void save(User user) {
        try {
            FileOutputStream fos = new FileOutputStream("src/" + user.getUsername() + ".txt");
            String username = user.getUsername() + "\n";
            fos.write(username.getBytes());

            String password = user.getPassword() + "\n";
            fos.write(password.getBytes());

            for (int i = 0; i < user.getVolunteers().size(); i++) {
                String name = user.getVolunteers().get(i).getName();
                String email = user.getVolunteers().get(i).getEmail();
                String role = user.getVolunteers().get(i).getRole();
                double hour = user.getVolunteers().get(i).getHour();
                String volunteerInfo = name + "|" + email + "|" + role + "|" + hour + "\n";
                fos.write(volunteerInfo.getBytes());
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
            f = new FileOutputStream("src/allUsernames.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < AccountManager.getAllUsers().size(); i++){
            String name = AccountManager.getAllUsers().get(i).getUsername() + "\n";
            try {
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
        //retrieves all usernames ever created
        ArrayList<String> usernames = new ArrayList<String>();
        while (sc.hasNextLine()){
            String str = sc.nextLine();
            usernames.add(str);
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
            User user = new User(username, password);
            AccountManager.getAllUsers().add(user);
            //for every user account, there is a list of volunteers
            while (sc2.hasNextLine()) {
                String volunteerInfo = sc2.nextLine();
                String volunteerName = volunteerInfo.substring(0, volunteerInfo.indexOf("|"));
                volunteerInfo = volunteerInfo.substring(volunteerInfo.indexOf("|") + 1);
                String volunteerEmail = volunteerInfo.substring(0, volunteerInfo.indexOf("|"));
                volunteerInfo = volunteerInfo.substring(volunteerInfo.indexOf("|") + 1);
                String volunteerRole = volunteerInfo.substring(0, volunteerInfo.indexOf("|"));
                volunteerInfo = volunteerInfo.substring(volunteerInfo.indexOf("|") + 1);
                double hour = Double.parseDouble(volunteerInfo);
                //creating volunteer
                Volunteer volunteer = new Volunteer(volunteerName, volunteerEmail, volunteerRole, hour);
                user.getVolunteers().add(volunteer);
            }
        }
    }
}