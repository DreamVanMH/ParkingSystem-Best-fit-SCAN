package Operating_System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private User user;
    private ParkingSystem system;
    private ParkingSpace allocatedSpace;
    private JTable userTable;
    private JTable parkingTable;

    public MainFrame(User user) throws SQLException {
        this.user = user;
        this.system = new ParkingSystem();
        
        setTitle("Parking Management System - " + user.getName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create and add the tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Users", createUserPanel());
        tabbedPane.add("Parking Spaces", createParkingPanel());
        add(tabbedPane, BorderLayout.CENTER);

        // Create and add the buttons panel
        JPanel buttonPanel = new JPanel();
        JButton parkInButton = new JButton("Park In");
        JButton parkOutButton = new JButton("Park Out");
        buttonPanel.add(parkInButton);
        buttonPanel.add(parkOutButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
     // Action listeners for buttons
        parkInButton.addActionListener(e -> {
			try {
				handleAllocateParking();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        parkOutButton.addActionListener(e -> {
			try {
				handleReleaseParking();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        // Load initial data
        loadUserData();
        loadParkingData();
    }
    
    
        
    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        userTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(userTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createParkingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        parkingTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(parkingTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void loadUserData() {
        try {
            List<User> users = DatabaseUtils.getAllUsers();
            DefaultTableModel model = new DefaultTableModel(new String[]{"Card ID", "Name", "Card Type", "Balance", "Role"}, 0);
            for (User user : users) {
                model.addRow(new Object[]{user.getCardId(), user.getName(), user.getCardType(), user.getBalance(), user.getRole()});
            }
            userTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleAllocateParking() throws SQLException {
        ParkingSpace allocatedSpace = system.allocateParkingSpace();
        if (allocatedSpace != null) {
            JOptionPane.showMessageDialog(this, "Assigned parking space: " + allocatedSpace.getSpaceId());
        } else {
            JOptionPane.showMessageDialog(this, "No available parking spaces.");
        }
        loadParkingData(); // Refresh data in the UI
    }

    private void handleReleaseParking() throws SQLException {
        String spaceIdStr = JOptionPane.showInputDialog(this, "Enter space ID to release:");
        if (spaceIdStr != null) {
            try {
                int spaceId = Integer.parseInt(spaceIdStr);
                boolean released = system.releaseParkingSpace(spaceId);
                if (released) {
                    JOptionPane.showMessageDialog(this, "Released parking space: " + spaceId);
                } else {
                    JOptionPane.showMessageDialog(this, "Parking space " + spaceId + " is not occupied or does not exist.");
                }
                loadParkingData(); // Refresh data in the UI
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid space ID entered.");
            }
        }
    }
    
    private void loadParkingData() {
        try {
            List<ParkingSpace> spaces = DatabaseUtils.getAllParkingSpaces();
            DefaultTableModel model = new DefaultTableModel(new String[]{"Space ID", "Occupied", "Distance from Entrance"}, 0);
            for (ParkingSpace space : spaces) {
                model.addRow(new Object[]{space.getSpaceId(), space.isOccupied(), space.getDistanceFromEntrance()});
            }
            parkingTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}