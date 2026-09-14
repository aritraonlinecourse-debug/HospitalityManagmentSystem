package com.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI {

    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public static void main(String[] args) {
        frame = new JFrame("Hospitality Management System");
        frame.setSize(900, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Menu panel
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton hotelBtn = new JButton("Add Hotels");
        JButton roomBtn = new JButton("Add Rooms");
        JButton guestBtn = new JButton("Add Guests");
        JButton reservationBtn = new JButton("Add Reservations");
        JButton retrieveBtn = new JButton("Retrieve Data");

        hotelBtn.addActionListener(e -> showCard("hotel"));
        roomBtn.addActionListener(e -> showCard("room"));
        guestBtn.addActionListener(e -> showCard("guest"));
        reservationBtn.addActionListener(e -> showCard("reservation"));
        retrieveBtn.addActionListener(e -> showCard("reservation")); // same view as before

        menuPanel.add(hotelBtn);
        menuPanel.add(roomBtn);
        menuPanel.add(guestBtn);
        menuPanel.add(reservationBtn);
        menuPanel.add(retrieveBtn);

        cardPanel.add(menuPanel, "menu");
        cardPanel.add(new HotelPanel().getPanel(), "hotel");
        cardPanel.add(new RoomPanel().getPanel(), "room");
        cardPanel.add(new GuestPanel().getPanel(), "guest");
        cardPanel.add(new ReservationPanel().getPanel(), "reservation");

        frame.add(cardPanel);
        cardLayout.show(cardPanel, "menu");

        frame.setVisible(true);
    }

    public static void showCard(String name) {
        cardLayout.show(cardPanel, name);
    }
}