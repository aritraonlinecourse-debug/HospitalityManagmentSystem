package com.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import com.database.Guest;
import com.database.GuestDAO;
import com.database.Reservation;
import com.database.ReservationDAO;
import com.database.Room;
import com.database.RoomsDAO;

public class RetrieveDataPanel {

    private JPanel panel;
    private JTextField nameField, checkInField;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    private GuestDAO guestDAO = new GuestDAO();
    private ReservationDAO reservationDAO = new ReservationDAO();
    private RoomsDAO roomDAO = new RoomsDAO();

    public RetrieveDataPanel() {
        panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Search by Guest Name and Check-In Date"));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.add(new JLabel("Guest Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Check-In Date (YYYY-MM-DD):"));
        checkInField = new JTextField();
        formPanel.add(checkInField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton searchBtn = new JButton("Search");
        JButton backBtn = new JButton("Back to Menu");
        buttonPanel.add(searchBtn);
        buttonPanel.add(backBtn);

        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new Object[]{
                "Guest ID", "Guest Name", "Room ID", "Room Type", "Hotel ID", "Check-In", "Check-Out", "Total Cost"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Results"));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        searchBtn.addActionListener(e -> search());
        backBtn.addActionListener(e -> MainGUI.showCard("menu"));
    }

    public JPanel getPanel() {
        return panel;
    }

    private void search() {
        tableModel.setRowCount(0);

        String nameQuery = nameField.getText().trim();
        String checkInText = checkInField.getText().trim();

        if (nameQuery.isEmpty() || checkInText.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Please enter both Guest Name and Check-In Date.");
            return;
        }

        LocalDate checkInDate;
        try {
            checkInDate = LocalDate.parse(checkInText);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Invalid date format. Use YYYY-MM-DD.");
            return;
        }

        List<Guest> allGuests = guestDAO.getAllGuests();
        List<Reservation> allReservations = reservationDAO.getAllReservations();

        boolean found = false;

        for (Guest g : allGuests) {
            if (g.getName().equalsIgnoreCase(nameQuery)) {
                for (Reservation r : allReservations) {
                    if (r.getGuestId() == g.getGuestId() && r.getCheckIn().equals(checkInDate)) {
                        Room room = roomDAO.getRoomById(r.getRoomId());
                        String roomType = room != null ? room.getType() : "Unknown";
                        int hotelId = room != null ? room.getHotelId() : -1;

                        tableModel.addRow(new Object[]{
                                g.getGuestId(), g.getName(), r.getRoomId(), roomType, hotelId,
                                r.getCheckIn(), r.getCheckOut(), r.getTotalCost()
                        });
                        found = true;
                    }
                }
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(panel, "No matching guest/reservation found for that name and check-in date.");
        }
    }
}