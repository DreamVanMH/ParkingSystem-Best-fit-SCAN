package Operating_System;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ParkingSystem {
    private List<ParkingSpace> parkingSpaces;

    public ParkingSystem() throws SQLException {
    	// Load parking spaces from the database at initialization
        this.parkingSpaces = DatabaseUtils.getAllParkingSpaces();
    }



    // Allocate the closest available parking space
    public ParkingSpace allocateParkingSpace() throws SQLException {
        ParkingSpace bestSpace = null;
        for (ParkingSpace space : parkingSpaces) {
            if (!space.isOccupied()) {
                if (bestSpace == null || space.getDistanceFromEntrance() < bestSpace.getDistanceFromEntrance()) {
                    bestSpace = space;
                }
            }
        }
        
        if (bestSpace != null) {
            bestSpace.setOccupied(true);
            DatabaseUtils.updateParkingSpace(bestSpace); // Update in the database
        }
        
        return bestSpace;
    }

    // Release the specified parking space by space ID
    public boolean releaseParkingSpace(int spaceId) throws SQLException {
        for (ParkingSpace space : parkingSpaces) {
            if (space.getSpaceId() == spaceId && space.isOccupied()) {
                space.setOccupied(false);
                DatabaseUtils.updateParkingSpace(space); // Update in the database
                return true;
            }
        }
        return false;
    }



    // User login method (kept unchanged for user verification)
    public static User login(String name, String password) throws SQLException {
        try (Connection conn = DatabaseUtils.getConnection()) {
            String query = "SELECT * FROM users WHERE name = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getInt("cardId"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getString("cardType"),
                    rs.getInt("balance"),
                    rs.getString("role")
                );
            }
        }
        return null; // 
    }
}