package com.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/* ReservationDAO (uses LocalDate in Reservation class) */
public class ReservationDAO {
    public boolean addReservation(Reservation r) {
        String sql = "INSERT INTO Reservation (guest_id, room_id, check_in, check_out, total_cost) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getGuestId());
            ps.setInt(2, r.getRoomId());
            ps.setDate(3, Date.valueOf(r.getCheckIn()));
            ps.setDate(4, Date.valueOf(r.getCheckOut()));
            ps.setDouble(5, r.getTotalCost());
            int affected = ps.executeUpdate();
            if (affected == 0) return false;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) r.setReservationId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public Reservation getReservationById(int id) {
        String sql = "SELECT * FROM Reservation WHERE reservation_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Reservation r = new Reservation();
                    r.setReservationId(rs.getInt("reservation_id"));
                    r.setGuestId(rs.getInt("guest_id"));
                    r.setRoomId(rs.getInt("room_id"));
                    r.setCheckIn(rs.getDate("check_in").toLocalDate());
                    r.setCheckOut(rs.getDate("check_out").toLocalDate());
                    r.setTotalCost(rs.getDouble("total_cost"));
                    return r;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateReservation(Reservation r) {
        String sql = "UPDATE Reservation SET guest_id = ?, room_id = ?, check_in = ?, check_out = ?, total_cost = ? WHERE reservation_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getGuestId());
            ps.setInt(2, r.getRoomId());
            ps.setDate(3, Date.valueOf(r.getCheckIn()));
            ps.setDate(4, Date.valueOf(r.getCheckOut()));
            ps.setDouble(5, r.getTotalCost());
            ps.setInt(6, r.getReservationId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteReservation(int id) {
        String sql = "DELETE FROM Reservation WHERE reservation_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM Reservation";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setReservationId(rs.getInt("reservation_id"));
                r.setGuestId(rs.getInt("guest_id"));
                r.setRoomId(rs.getInt("room_id"));
                r.setCheckIn(rs.getDate("check_in").toLocalDate());
                r.setCheckOut(rs.getDate("check_out").toLocalDate());
                r.setTotalCost(rs.getDouble("total_cost"));
                list.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
