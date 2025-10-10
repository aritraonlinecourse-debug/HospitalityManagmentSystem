package com.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
import com.database.Reservation;
import com.database.ReservationDAO;

public class ReservationGUI {

    private JFrame frame;
    private JTextField idField, guestIdField, roomIdField, checkInField, checkOutField, costField;
    private JTable reservationTable;
    private ReservationDAO reservationDAO = new ReservationDAO();
    private DefaultTableModel tableModel;

    public ReservationGUI() {
        frame = new JFrame("Reservation Management");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top Panel with form and buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Reservation Details"));

        // Form panel for labels and fields (6 rows, 2 columns)
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.add(new JLabel("Reservation ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Guest ID:"));
        guestIdField = new JTextField();
        formPanel.add(guestIdField);

        formPanel.add(new JLabel("Room ID:"));
        roomIdField = new JTextField();
        formPanel.add(roomIdField);

        formPanel.add(new JLabel("Check-In (YYYY-MM-DD):"));
        checkInField = new JTextField();
        formPanel.add(checkInField);

        formPanel.add(new JLabel("Check-Out (YYYY-MM-DD):"));
        checkOutField = new JTextField();
        formPanel.add(checkOutField);

        formPanel.add(new JLabel("Total Cost:"));
        costField = new JTextField();
        formPanel.add(costField);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton addBtn = new JButton("Add Reservation");
        JButton updateBtn = new JButton("Update Reservation");
        JButton deleteBtn = new JButton("Delete Reservation");
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        // Assemble topPanel
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Center Panel for table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Guest ID", "Room ID", "Check-In", "Check-Out", "Total Cost"}, 0);
        reservationTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reservationTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Reservations List"));

        // Add panels to frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Load table data
        loadTableData();

        // Event handling
        addBtn.addActionListener(e -> addReservation());
        updateBtn.addActionListener(e -> updateReservation());
        deleteBtn.addActionListener(e -> deleteReservation());
        reservationTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = reservationTable.getSelectedRow();
                if (selectedRow >= 0) {
                    idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    guestIdField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    roomIdField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    checkInField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    checkOutField.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    costField.setText(tableModel.getValueAt(selectedRow, 5).toString());
                }
            }
        });

        frame.setVisible(true);
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<Reservation> list = reservationDAO.getAllReservations();
        for (Reservation r : list) {
            tableModel.addRow(new Object[]{r.getReservationId(), r.getGuestId(), r.getRoomId(), r.getCheckIn(), r.getCheckOut(), r.getTotalCost()});
        }
    }

    private void addReservation() {
        try {
            Reservation r = new Reservation(0, Integer.parseInt(guestIdField.getText()), Integer.parseInt(roomIdField.getText()),
                    LocalDate.parse(checkInField.getText()), LocalDate.parse(checkOutField.getText()), Double.parseDouble(costField.getText()));
            if (reservationDAO.addReservation(r)) {
                JOptionPane.showMessageDialog(frame, "Reservation added successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(frame, "Error adding reservation.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input.");
        }
    }

    private void updateReservation() {
        try {
            Reservation r = new Reservation(Integer.parseInt(idField.getText()), Integer.parseInt(guestIdField.getText()), Integer.parseInt(roomIdField.getText()),
                    LocalDate.parse(checkInField.getText()), LocalDate.parse(checkOutField.getText()), Double.parseDouble(costField.getText()));
            if (reservationDAO.updateReservation(r)) {
                JOptionPane.showMessageDialog(frame, "Reservation updated successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(frame, "Error updating reservation.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input.");
        }
    }

    private void deleteReservation() {
        try {
            if (reservationDAO.deleteReservation(Integer.parseInt(idField.getText()))) {
                JOptionPane.showMessageDialog(frame, "Reservation deleted successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(frame, "Error deleting reservation.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid Reservation ID.");
        }
    }

    private void clearFields() {
        idField.setText("");
        guestIdField.setText("");
        roomIdField.setText("");
        checkInField.setText("");
        checkOutField.setText("");
        costField.setText("");
    }

    public static void showGUI() {
        new ReservationGUI();
    }
}
