package com.splitwise.service;


import com.splitwise.dao.Group;

import com.splitwise.repository.ExpenseRepository;
import com.splitwise.repository.GroupRepository;



import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GroupService {
    private final  GroupRepository groupRepo;
    private static UserService userService;

    public GroupService(GroupRepository groupRepo, UserService userService){
        this.groupRepo = groupRepo;
        this.userService = userService;
    }
    public Group createGroup(String groupName, List<String> userNames, String createdBy) throws Exception {

        if(userService.validateUsers(userNames)){
            Group group = new Group(groupName,null, createdBy, createdBy, LocalTime.now(), LocalTime.now(),userNames );
            groupRepo.create(group);
            userService.addGroupToUsers(userNames,groupName);
            return group;
        }
        else {
            throw new Exception("All users must be registered before creating group");
        }

    }


    public boolean validateUsersAgainstGroup(String groupName, List<String> users){
        Group group = groupRepo.findGroupByName(groupName);
        List<String> groupUsers = group.getUsers();
        for(String user: users){
            if(!groupUsers.contains(user)) return false;
        }
        return true;
    }


    public ReentrantReadWriteLock.ReadLock getReadLockByName(String groupName){
        Group group = groupRepo.findGroupByName(groupName);
        ReentrantReadWriteLock.ReadLock read = group.getLock().readLock();
        return read;
    }

    public ReentrantReadWriteLock.WriteLock getWriteLockByName(String groupName){
        Group group = groupRepo.findGroupByName(groupName);
        ReentrantReadWriteLock.WriteLock write = group.getLock().writeLock();
        return write;
    }

}
