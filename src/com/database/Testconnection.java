package com.database;

import java.sql.Connection;

public class Testconnection {
    public static void main(String[] args) {
        try (Connection c = DatabaseConnector.getConnection()) {
            if (c != null && !c.isClosed()) System.out.println("Connection OK");
            else System.out.println("Connection failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
