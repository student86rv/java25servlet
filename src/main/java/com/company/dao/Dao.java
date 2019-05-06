package com.company.dao;

import com.company.model.User;

import java.util.List;

public interface Dao {

    void add(User user);

    void remove(User user);

    void update(User user);

    User get(int id);

    User getByName(String name);

    List<User> getAllUsers();

    List<User> getUsersByAge(int fromAge, int toAge);
}
