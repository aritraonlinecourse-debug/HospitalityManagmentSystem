package com.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/* HotelDAO */
public class HotelDAO {
    public boolean addHotel(Hotel hotel) {
        String sql = "INSERT INTO Hotel (name, location, amenities) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, hotel.getName());
            ps.setString(2, hotel.getLocation());
            ps.setString(3, hotel.getAmenities());
            int affected = ps.executeUpdate();
            if (affected == 0) return false;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) hotel.setHotelId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public Hotel getHotelById(int id) {
        String sql = "SELECT * FROM Hotel WHERE hotel_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Hotel(rs.getInt("hotel_id"), rs.getString("name"), rs.getString("location"), rs.getString("amenities"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateHotel(Hotel hotel) {
        String sql = "UPDATE Hotel SET name = ?, location = ?, amenities = ? WHERE hotel_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hotel.getName());
            ps.setString(2, hotel.getLocation());
            ps.setString(3, hotel.getAmenities());
            ps.setInt(4, hotel.getHotelId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteHotel(int id) {
        String sql = "DELETE FROM Hotel WHERE hotel_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Hotel> getAllHotels() {
        List<Hotel> list = new ArrayList<>();
        String sql = "SELECT * FROM Hotel";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Hotel(rs.getInt("hotel_id"), rs.getString("name"), rs.getString("location"), rs.getString("amenities")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
