package com.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import com.database.Guest;
import com.database.GuestDAO;

public class GuestGUI {

    private JFrame frame;
    private JTextField idField, nameField, emailField, phoneField;
    private JTable guestTable;
    private GuestDAO guestDAO = new GuestDAO();
    private DefaultTableModel tableModel;

    public GuestGUI() {
        frame = new JFrame("Guest Management");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top Panel with form and buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Guest Details"));

        // Form panel for labels and fields (4 rows, 2 columns)
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

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton addBtn = new JButton("Add Guest");
        JButton updateBtn = new JButton("Update Guest");
        JButton deleteBtn = new JButton("Delete Guest");
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        // Assemble topPanel
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Center Panel for table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Phone"}, 0);
        guestTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(guestTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Guest List"));

        // Add panels to frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Load table data
        loadTableData();

        // Event handling
        addBtn.addActionListener(e -> addGuest());
        updateBtn.addActionListener(e -> updateGuest());
        deleteBtn.addActionListener(e -> deleteGuest());
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

        frame.setVisible(true);
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
                JOptionPane.showMessageDialog(frame, "Guest added successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(frame, "Error adding guest.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input.");
        }
    }

    private void updateGuest() {
        try {
            Guest g = new Guest(Integer.parseInt(idField.getText()), nameField.getText(), emailField.getText(), phoneField.getText());
            if (guestDAO.updateGuest(g)) {
                JOptionPane.showMessageDialog(frame, "Guest updated successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(frame, "Error updating guest.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input.");
        }
    }

    private void deleteGuest() {
        try {
            if (guestDAO.deleteGuest(Integer.parseInt(idField.getText()))) {
                JOptionPane.showMessageDialog(frame, "Guest deleted successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(frame, "Error deleting guest.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid Guest ID.");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }

    public static void showGUI() {
        new GuestGUI();
    }
}
