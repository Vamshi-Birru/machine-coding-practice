package com.splitwise.repository;

import com.splitwise.dao.Group;

import java.util.HashMap;
import java.util.Map;

public class GroupRepository {
    Map<String, Group> collection;

    public GroupRepository(){
        this.collection = new HashMap<>();
    }
    public Group findGroupByName(String groupName){
        return collection.get(groupName);
    }

    public void create(Group group){
        collection.put(group.getGroupName(),group );
    }


    public void updateGroup(Group group){
        collection.put(group.getGroupName(), group);
    }
}
