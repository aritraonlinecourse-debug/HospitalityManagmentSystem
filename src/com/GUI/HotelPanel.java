package com.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.database.Hotel;
import com.database.HotelDAO;

public class HotelPanel {

    private JPanel panel;
    private JTextField idField, nameField, locationField, amenitiesField;
    private JTextField singlePriceField, couplePriceField, familyPriceField;
    private JTable hotelTable;
    private HotelDAO hotelDAO = new HotelDAO();
    private DefaultTableModel tableModel;

    public HotelPanel() {
        panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Hotel Details"));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Location:"));
        locationField = new JTextField();
        formPanel.add(locationField);

        formPanel.add(new JLabel("Amenities:"));
        amenitiesField = new JTextField();
        formPanel.add(amenitiesField);

        formPanel.add(new JLabel("Single Room Price:"));
        singlePriceField = new JTextField();
        formPanel.add(singlePriceField);

        formPanel.add(new JLabel("Couple Room Price:"));
        couplePriceField = new JTextField();
        formPanel.add(couplePriceField);

        formPanel.add(new JLabel("Family Room Price:"));
        familyPriceField = new JTextField();
        formPanel.add(familyPriceField);

        idField = new JTextField(); // kept internally, not shown — auto-filled when a row is clicked

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton addBtn = new JButton("Add Hotel");
        JButton updateBtn = new JButton("Update Hotel");
        JButton deleteBtn = new JButton("Delete Hotel");
        JButton backBtn = new JButton("Back to Menu");
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(backBtn);

        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Location", "Amenities", "Single Room Price", "Couple Room Price", "Family Room Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        hotelTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(hotelTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Hotels List"));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadTableData();

        addBtn.addActionListener(e -> addHotel());
        updateBtn.addActionListener(e -> updateHotel());
        deleteBtn.addActionListener(e -> deleteHotel());
        backBtn.addActionListener(e -> MainGUI.showCard("menu"));

        hotelTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = hotelTable.getSelectedRow();
                if (selectedRow >= 0) {
                    idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    locationField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    amenitiesField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    singlePriceField.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    couplePriceField.setText(tableModel.getValueAt(selectedRow, 5).toString());
                    familyPriceField.setText(tableModel.getValueAt(selectedRow, 6).toString());
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<Hotel> hotels = hotelDAO.getAllHotels();
        for (Hotel h : hotels) {
            tableModel.addRow(new Object[]{
                    h.getHotelId(), h.getName(), h.getLocation(), h.getAmenities(),
                    h.getSingleRoomPrice(), h.getCoupleRoomPrice(), h.getFamilyRoomPrice()
            });
        }
    }

    private void addHotel() {
        try {
            Hotel hotel = new Hotel(0, nameField.getText(), locationField.getText(), amenitiesField.getText(),
                    Double.parseDouble(singlePriceField.getText()),
                    Double.parseDouble(couplePriceField.getText()),
                    Double.parseDouble(familyPriceField.getText()));
            if (hotelDAO.addHotel(hotel)) {
                JOptionPane.showMessageDialog(panel, "Hotel added successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(panel, "Error adding hotel.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Please enter valid numeric prices.");
        }
    }

    private void updateHotel() {
        try {
            int id = Integer.parseInt(idField.getText());
            Hotel hotel = new Hotel(id, nameField.getText(), locationField.getText(), amenitiesField.getText(),
                    Double.parseDouble(singlePriceField.getText()),
                    Double.parseDouble(couplePriceField.getText()),
                    Double.parseDouble(familyPriceField.getText()));
            if (hotelDAO.updateHotel(hotel)) {
                JOptionPane.showMessageDialog(panel, "Hotel updated successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(panel, "Error updating hotel.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Invalid Hotel ID or price.");
        }
    }

    private void deleteHotel() {
        try {
            int id = Integer.parseInt(idField.getText());
            if (hotelDAO.deleteHotel(id)) {
                JOptionPane.showMessageDialog(panel, "Hotel deleted successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(panel, "Error deleting hotel.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Invalid Hotel ID.");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        amenitiesField.setText("");
        singlePriceField.setText("");
        couplePriceField.setText("");
        familyPriceField.setText("");
    }
}