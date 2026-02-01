package com.splitwise.service;

import com.splitwise.dao.User;
import com.splitwise.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepo;
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User createUser(String userName){
        User user = new User(userName);
        userRepo.create(user);
        return user;
    }
    public boolean validateUsers(List<String> userNames){

        for(String userName: userNames){
            if(!userRepo.validateUserByName(userName)) return false;
        }

        return true;
    }
    public void addGroupToUsers(List<String> userNames, String groupName){
        for(String userName: userNames){
            User user = userRepo.findUserByName(userName);
            List<String> groups = user.getGroups();
            groups.add(groupName);
            user.setGroups(groups);
            userRepo.updateUser(user);
        }
    }
}
