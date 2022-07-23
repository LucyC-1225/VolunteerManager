import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AccountManager {
    private JFrame frame;
    private JPanel panel;
    private JButton login;
    private JButton signup;
    private User user;
    private static ArrayList<User> allUsers;

    public AccountManager() {
        allUsers = new ArrayList<User>();
        frame = new JFrame("Login Frame");
        frame.setSize(400,500);
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        signup = new JButton("Sign up");
        signup.addActionListener(this::actionPerformed);
        panel.add(signup, c);

        c.gridy = 1;
        login = new JButton("Login");
        login.addActionListener(this::actionPerformed);
        panel.add(login, c);


        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
        GUI.retrieve();
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == signup) {
            String username = JOptionPane.showInputDialog(frame, "Enter username");
            String password = JOptionPane.showInputDialog(frame, "Enter password");
            if (usernameExist(username)) {
                JOptionPane.showMessageDialog(frame, "USERNAME ALREADY EXISTS. PLEASE LOGIN");
            } else if (username != null && password != null) {
                user = new User(username, password);
                allUsers.add(user);
            }
        }
        if (source == login) {
            while (true){
                String username = JOptionPane.showInputDialog(frame, "Enter your username");
                String password = JOptionPane.showInputDialog(frame, "Enter your password");
                if (usernameExist(username)) {
                    if (getUserByUsername(username).getPassword().equals(password)) {
                        user = getUserByUsername(username);
                        break;
                    } else {
                        JOptionPane.showMessageDialog(frame, "WRONG USERNAME/PASSWORD");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "USERNAME DOES NOT EXIST. PLEASE SIGN UP FIRST");
                    break;
                }
            }
            GUI start = new GUI(user);
            frame.setVisible(false);
            start.initialize();
        }
    }
    public boolean usernameExist(String username) {
        for (int i = 0; i < allUsers.size(); i++) {
            if (username.equals(allUsers.get(i).getUsername())) {
                return true;
            }
        }
        return false;
    }
    public User getUserByUsername(String username) {
        for (int i = 0; i < allUsers.size(); i++) {
            if (username.equals(allUsers.get(i).getUsername())) {
                return allUsers.get(i);
            }
        }
        return null;
    }
    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }
}
