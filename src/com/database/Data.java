package com.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Data {

	public static void main(String[] args) {
		 try {
    		 Class.forName("com.mysql.cj.jdbc.Driver");
    		 //database url->provides database name,location
    		 Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitaility_management_system","root","aritra@123");
    		 //create table which will done by statement class
    		 Statement smt=con.createStatement();
    		 //query->communication with db
    		 smt.executeUpdate("CREATE TABLE Hotel (hotel_id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(150), location VARCHAR(200), amenities TEXT)");
    		 System.out.println("Table created Successfully");
    		 smt.executeUpdate("CREATE TABLE Rooms (room_id INT AUTO_INCREMENT PRIMARY KEY, hotel_id INT NOT NULL, room_number VARCHAR(20), type VARCHAR(50), price DECIMAL(10,2), status VARCHAR(30), FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id) ON DELETE CASCADE)");
    		 System.out.println("Table created Successfully");
    		 smt.executeUpdate("CREATE TABLE Guest (guest_id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(150), email VARCHAR(120), phone VARCHAR(30))");
    		 System.out.println("Table created Successfully");
    		 smt.executeUpdate("CREATE TABLE Reservation (reservation_id INT AUTO_INCREMENT PRIMARY KEY, guest_id INT NOT NULL, room_id INT NOT NULL, check_in DATE NOT NULL, check_out DATE NOT NULL, total_cost DECIMAL(10,2), FOREIGN KEY (guest_id) REFERENCES Guest(guest_id) ON DELETE CASCADE, FOREIGN KEY (room_id) REFERENCES Rooms(room_id) ON DELETE CASCADE)");
    		 System.out.println("Table created Successfully");
    		 con.close();
    	 }
		 catch(Exception e) {
    		 System.out.println(e);
    	 }

	}

}
