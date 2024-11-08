package Operating_System;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField nameField;
    private JPasswordField passwordField;
    private ParkingSystem system;

    

    public LoginFrame() throws SQLException {
        system = new ParkingSystem();
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Username:");
        nameLabel.setBounds(30, 30, 80, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 30, 150, 25);
        add(nameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 70, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 70, 150, 25);
        add(passwordField);


        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 110, 80, 25);
        add(loginButton);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                
                try {
                    User user = system.login(name, password);
                    //System.out.println("User role: " + user.getRole());
                    
                    if (user != null && "ADMIN".equals(user.getRole())) {
                        new MainFrame(user).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Access Denied. You are not an administrator.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Login Failed. Check your username and password.");
                    ex.printStackTrace();
                }
            }
        });
    }

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
        
        }
       

    public static void main(String[] args) throws SQLException {
        new LoginFrame().setVisible(true);  // Ensure LoginFrame inherits from JFrame
    }
}
    
