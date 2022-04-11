package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery
                            ("CREATE TABLE IF NOT EXISTS user (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR (255), lastName VARCHAR(255), age INT NOT NULL)")
                    .addEntity(User.class).executeUpdate();
            transaction.commit();
            transaction.rollback();
            System.out.println("Table is created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery
                            ("DROP TABLE IF EXISTS user")
                    .addEntity(User.class).executeUpdate();
            transaction.commit();
            transaction.rollback();
            System.out.println("Table is dropped.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            transaction.rollback();
            System.out.println("New User is saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            transaction.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            users = session.createSQLQuery("SELECT * FROM user").addEntity(User.class).list();
            transaction.commit();
            transaction.rollback();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery
                            ("DELETE FROM user")
                    .addEntity(User.class).executeUpdate();
            transaction.commit();
            transaction.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
