package com.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import com.database.Guest;
import com.database.GuestDAO;

public class GuestPanel {

    private JPanel panel;
    private JTextField idField, nameField, emailField, phoneField;
    private JTable guestTable;
    private GuestDAO guestDAO = new GuestDAO();
    private DefaultTableModel tableModel;

    public GuestPanel() {
        panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Guest Details"));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.add(new JLabel("Guest ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton addBtn = new JButton("Add Guest");
        JButton updateBtn = new JButton("Update Guest");
        JButton deleteBtn = new JButton("Delete Guest");
        JButton backBtn = new JButton("Back to Menu");
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(backBtn);

        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Phone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        guestTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(guestTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Guest List"));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadTableData();

        addBtn.addActionListener(e -> addGuest());
        updateBtn.addActionListener(e -> updateGuest());
        deleteBtn.addActionListener(e -> deleteGuest());
        backBtn.addActionListener(e -> MainGUI.showCard("menu"));

        guestTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = guestTable.getSelectedRow();
                if (selectedRow >= 0) {
                    idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    emailField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    phoneField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<Guest> guests = guestDAO.getAllGuests();
        for (Guest g : guests) {
            tableModel.addRow(new Object[]{g.getGuestId(), g.getName(), g.getEmail(), g.getPhone()});
        }
    }

    private void addGuest() {
        try {
            Guest g = new Guest(0, nameField.getText(), emailField.getText(), phoneField.getText());
            if (guestDAO.addGuest(g)) {
                JOptionPane.showMessageDialog(panel, "Guest added successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(panel, "Error adding guest.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Invalid input.");
        }
    }

    private void updateGuest() {
        try {
            Guest g = new Guest(Integer.parseInt(idField.getText()), nameField.getText(), emailField.getText(), phoneField.getText());
            if (guestDAO.updateGuest(g)) {
                JOptionPane.showMessageDialog(panel, "Guest updated successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(panel, "Error updating guest.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Invalid input.");
        }
    }

    private void deleteGuest() {
        try {
            if (guestDAO.deleteGuest(Integer.parseInt(idField.getText()))) {
                JOptionPane.showMessageDialog(panel, "Guest deleted successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(panel, "Error deleting guest.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Invalid Guest ID.");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }
}