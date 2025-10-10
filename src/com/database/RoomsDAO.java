package com.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/* RoomDAO */
public class RoomsDAO {
    public boolean addRoom(Room room) {
        String sql = "INSERT INTO Rooms (hotel_id, room_number, type, price, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, room.getHotelId());
            ps.setString(2, room.getRoomNumber());
            ps.setString(3, room.getType());
            ps.setDouble(4, room.getPrice());
            ps.setString(5, room.getStatus());
            int affected = ps.executeUpdate();
            if (affected == 0) return false;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) room.setRoomId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public Room getRoomById(int id) {
        String sql = "SELECT * FROM Rooms WHERE room_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Room(rs.getInt("room_id"), rs.getInt("hotel_id"), rs.getString("room_number"),
                            rs.getString("type"), rs.getDouble("price"), rs.getString("status"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateRoom(Room room) {
        String sql = "UPDATE Rooms SET hotel_id = ?, room_number = ?, type = ?, price = ?, status = ? WHERE room_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, room.getHotelId());
            ps.setString(2, room.getRoomNumber());
            ps.setString(3, room.getType());
            ps.setDouble(4, room.getPrice());
            ps.setString(5, room.getStatus());
            ps.setInt(6, room.getRoomId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteRoom(int id) {
        String sql = "DELETE FROM Rooms WHERE room_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM Rooms";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Room(rs.getInt("room_id"), rs.getInt("hotel_id"), rs.getString("room_number"),
                        rs.getString("type"), rs.getDouble("price"), rs.getString("status")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
