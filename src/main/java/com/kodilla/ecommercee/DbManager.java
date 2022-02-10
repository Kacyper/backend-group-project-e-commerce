package com.kodilla.ecommercee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum DbManager {

    INSTANCE;

    private Connection connection;

    DbManager() {
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", "kodilla_user");
        connectionProperties.put("password", "kodilla_password");

        try {

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/kodilla_project" +
                            "?serverTimezone=Europe/Warsaw" +
                            "&useSSL=False&allowPublicKeyRetrieval=true",
                    connectionProperties);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DbManager getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }
}
