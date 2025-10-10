package com.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainGUI {

    public static void main(String[] args) {
        // Main Frame
        JFrame frame = new JFrame("Hospitality Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        // Buttons for each module
        JButton hotelBtn = new JButton("Add Hotels");
        JButton roomBtn = new JButton("Add Rooms");
        JButton guestBtn = new JButton("Add Guests");
        JButton reservationBtn = new JButton("Add Reservations");
        JButton retrieveBtn = new JButton("Retrieve Data");

        // Add action listeners
        hotelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call hotel management GUI method
                 HotelGUI.showGUI();
            }
        });

        roomBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call room management GUI method
                 RoomGUI.showGUI();
            }
        });

        guestBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call guest management GUI method
                 GuestGUI.showGUI();
            }
        });

        reservationBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call reservation management GUI method
                 ReservationGUI.showGUI();
            }
        });

        retrieveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 HotelGUI.showGUI();       // opens hotel GUI
                 RoomGUI.showGUI();        // opens room GUI
                 GuestGUI.showGUI();       // opens guest GUI
                 ReservationGUI.showGUI(); // opens reservation GUI
            }
        });

        // Add buttons to panel
        panel.add(hotelBtn);
        panel.add(roomBtn);
        panel.add(guestBtn);
        panel.add(reservationBtn);
        panel.add(retrieveBtn);

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }
}
