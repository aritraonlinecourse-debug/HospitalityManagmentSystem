package com.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import com.database.Room;
import com.database.RoomsDAO;

public class RoomPanel {

    private JPanel panel;
    private JTextField idField, hotelIdField, roomNumberField, typeField, priceField, statusField;
    private JTable roomTable;
    private RoomsDAO roomDAO = new RoomsDAO();
    private DefaultTableModel tableModel;

    public RoomPanel() {
        panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Room Details"));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        idField = new JTextField(); // kept internally, not shown — auto-filled when a row is clicked

        formPanel.add(new JLabel("Hotel ID:"));
        hotelIdField = new JTextField();
        formPanel.add(hotelIdField);

        formPanel.add(new JLabel("Room Number:"));
        roomNumberField = new JTextField();
        formPanel.add(roomNumberField);

        formPanel.add(new JLabel("Type:"));
        typeField = new JTextField();
        formPanel.add(typeField);

        formPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        formPanel.add(priceField);

        formPanel.add(new JLabel("Status:"));
        statusField = new JTextField();
        formPanel.add(statusField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton addBtn = new JButton("Add Room");
        JButton updateBtn = new JButton("Update Room");
        JButton deleteBtn = new JButton("Delete Room");
        JButton backBtn = new JButton("Back to Menu");
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(backBtn);

        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new Object[]{"ID","Hotel ID","Room Number","Type","Price","Status"},0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(roomTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Rooms List"));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadTableData();

        addBtn.addActionListener(e -> addRoom());
        updateBtn.addActionListener(e -> updateRoom());
        deleteBtn.addActionListener(e -> deleteRoom());
        backBtn.addActionListener(e -> MainGUI.showCard("menu"));

        roomTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = roomTable.getSelectedRow();
                if(selectedRow >=0){
                    idField.setText(tableModel.getValueAt(selectedRow,0).toString());
                    hotelIdField.setText(tableModel.getValueAt(selectedRow,1).toString());
                    roomNumberField.setText(tableModel.getValueAt(selectedRow,2).toString());
                    typeField.setText(tableModel.getValueAt(selectedRow,3).toString());
                    priceField.setText(tableModel.getValueAt(selectedRow,4).toString());
                    statusField.setText(tableModel.getValueAt(selectedRow,5).toString());
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<Room> rooms = roomDAO.getAllRooms();
        for(Room r: rooms){
            tableModel.addRow(new Object[]{r.getRoomId(),r.getHotelId(),r.getRoomNumber(),r.getType(),r.getPrice(),r.getStatus()});
        }
    }

    private void addRoom() {
        try {
            Room r = new Room(0, Integer.parseInt(hotelIdField.getText()), roomNumberField.getText(),
                    typeField.getText(), Double.parseDouble(priceField.getText()), statusField.getText());
            if(roomDAO.addRoom(r)){
                JOptionPane.showMessageDialog(panel,"Room added successfully!");
                loadTableData();
                clearFields();
            } else JOptionPane.showMessageDialog(panel,"Error adding room.");
        } catch(Exception e){ JOptionPane.showMessageDialog(panel,"Invalid input."); }
    }

    private void updateRoom() {
        try {
            Room r = new Room(Integer.parseInt(idField.getText()), Integer.parseInt(hotelIdField.getText()), roomNumberField.getText(),
                    typeField.getText(), Double.parseDouble(priceField.getText()), statusField.getText());
            if(roomDAO.updateRoom(r)){
                JOptionPane.showMessageDialog(panel,"Room updated successfully!");
                loadTableData();
                clearFields();
            } else JOptionPane.showMessageDialog(panel,"Error updating room.");
        } catch(Exception e){ JOptionPane.showMessageDialog(panel,"Invalid input."); }
    }

    private void deleteRoom() {
        try{
            if(roomDAO.deleteRoom(Integer.parseInt(idField.getText()))){
                JOptionPane.showMessageDialog(panel,"Room deleted successfully!");
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(panel,"Error deleting room.");
            }
        } catch(Exception e){ JOptionPane.showMessageDialog(panel,"Invalid Room ID."); }
    }

    private void clearFields(){
        idField.setText("");
        hotelIdField.setText("");
        roomNumberField.setText("");
        typeField.setText("");
        priceField.setText("");
        statusField.setText("");
    }
}