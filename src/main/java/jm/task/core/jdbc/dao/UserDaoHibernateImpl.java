package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery
                        ("CREATE TABLE IF NOT EXISTS user (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR (255), lastName VARCHAR(255), age INT NOT NULL)")
                .addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();
        System.out.println("Table is created");
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery
                        ("DROP TABLE IF EXISTS user")
                .addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();
        System.out.println("Table is dropped.");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        transaction.commit();
        session.close();
        System.out.println("New User is saved.");
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery("SELECT * FROM user").addEntity(User.class);
        List<User> users = query.list();
        transaction.commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery
                        ("DELETE FROM user")
                .addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

}
