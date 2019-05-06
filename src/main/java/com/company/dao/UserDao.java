package com.company.dao;

import com.company.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UserDao implements Dao{

    private SessionFactory sessionFactory;

    public UserDao() {
        java.util.Properties properties = new java.util.Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("local.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sessionFactory = new Configuration().configure().addProperties(properties).buildSessionFactory();
    }

    public void close() {
        sessionFactory.close();
    }

    public void add(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
    }

    public void remove(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        }
    }

    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }
    }

    public User get(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM User WHERE _id = :id ", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }

    public User getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM User WHERE name = :name ", User.class)
                    .setParameter("name", name)
                    .list().get(0);
        }
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }

    public List<User> getUsersByAge(int fromAge, int toAge) {
        Calendar fromDate = new GregorianCalendar();
        Calendar toDate = new GregorianCalendar();
        fromDate.add(Calendar.YEAR, -fromAge);
        toDate.add(Calendar.YEAR, -toAge);
        Timestamp beginDate = new Timestamp(toDate.getTimeInMillis());
        Timestamp endDate = new Timestamp(fromDate.getTimeInMillis());

        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM User WHERE birth_date BETWEEN :from AND :to ORDER BY birth_date", User.class)
                    .setParameter("from", beginDate)
                    .setParameter("to", endDate)
                    .list();
        }
    }
}
