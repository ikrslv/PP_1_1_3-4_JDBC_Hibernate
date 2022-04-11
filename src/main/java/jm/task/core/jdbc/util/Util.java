package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private static Util CONNECTION;

    private Util() {
    }

    private static Util getCONNECTION() {
        if (CONNECTION == null) {
            CONNECTION = new Util();
        }
        return CONNECTION;
    }

    private static Connection connection = null;
    private static String user = "root";
    private static String password = "8Cim3_b6r";
    private static String urlConnection = "jdbc:mysql://localhost:3306/usersdb";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(urlConnection, user, password);
            System.out.println("Connection is completed!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

