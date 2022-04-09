package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private static Connection connection = null;

    private Util() {

    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/usersdb", "root", "8Cim3_b6r");
            System.out.println("Connection is completed!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Для Hibernate
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties properties = new Properties();

                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                properties.put(Environment.HBM2DDL_AUTO, "update");
                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.USER, "root");
                properties.put(Environment.PASS, "8Cim3_b6r");
                properties.put(Environment.URL, "jdbc:mysql://localhost:3306/usersdb");

                properties.put(Environment.SHOW_SQL, "true");

                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                properties.put(Environment.HBM2DDL_AUTO, "create");

                configuration.setProperties(properties);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Connection is completed by Hibernate!");
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}

