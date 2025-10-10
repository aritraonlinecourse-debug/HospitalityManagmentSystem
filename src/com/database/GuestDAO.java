package com.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/* GuestDAO */
public class GuestDAO {
    public boolean addGuest(Guest guest) {
        String sql = "INSERT INTO Guest (name, email, phone) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, guest.getName());
            ps.setString(2, guest.getEmail());
            ps.setString(3, guest.getPhone());
            int affected = ps.executeUpdate();
            if (affected == 0) return false;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) guest.setGuestId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public Guest getGuestById(int id) {
        String sql = "SELECT * FROM Guest WHERE guest_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Guest(rs.getInt("guest_id"), rs.getString("name"), rs.getString("email"), rs.getString("phone"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateGuest(Guest guest) {
        String sql = "UPDATE Guest SET name = ?, email = ?, phone = ? WHERE guest_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, guest.getName());
            ps.setString(2, guest.getEmail());
            ps.setString(3, guest.getPhone());
            ps.setInt(4, guest.getGuestId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteGuest(int id) {
        String sql = "DELETE FROM Guest WHERE guest_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Guest> getAllGuests() {
        List<Guest> list = new ArrayList<>();
        String sql = "SELECT * FROM Guest";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Guest(rs.getInt("guest_id"), rs.getString("name"), rs.getString("email"), rs.getString("phone")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
