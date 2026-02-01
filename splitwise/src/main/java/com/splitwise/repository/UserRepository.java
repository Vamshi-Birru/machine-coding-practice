package com.splitwise.repository;

import com.splitwise.dao.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    Map<String, User> collection;

    public UserRepository(){
        this.collection = new HashMap<>();
    }
    public void create(User user){
        collection.put(user.getUserName(), user);
    }
    public User findUserByName(String userName){
        return collection.get(userName);
    }

    public boolean validateUserByName(String userName){
        return collection.containsKey(userName);
    }

    public void updateUser(User user){
        collection.put(user.getUserName(), user);
    }
}
