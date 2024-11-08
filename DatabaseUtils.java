package Operating_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {
    private static final String URL = "jdbc:mysql://localhost:3306/parking_system"; // Replace with your database address
    private static final String USER = "root";   // Database username
    private static final String PASSWORD = "Os@12345678"; // Database password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }



    public static List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                users.add(new User(
                    rs.getInt("cardId"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getString("cardType"),
                    rs.getInt("balance"),
                    rs.getString("role")
                ));
            }
        }
        return users;
    }

    public static List<ParkingSpace> getAllParkingSpaces() throws SQLException {
        List<ParkingSpace> spaces = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "SELECT spaceId, isOccupied, distanceFromEntrance FROM parking_spaces";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                spaces.add(new ParkingSpace(
                    rs.getInt("spaceId"),
                    rs.getBoolean("isOccupied"),
                    rs.getInt("distanceFromEntrance")
                ));
            }
        }
        return spaces;
    }



    public static void updateParkingSpace(ParkingSpace space) throws SQLException {
        try (Connection conn = getConnection()) {
            String query = "UPDATE parking_spaces SET isOccupied = ? WHERE spaceId = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, space.isOccupied());
            pstmt.setInt(2, space.getSpaceId());
            pstmt.executeUpdate();
        }
    }
    
   
}