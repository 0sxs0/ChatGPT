package com.example.chatgpt.service;

import com.example.chatgpt.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @Author Jialing Sun
 * @Date 2023/2/15 18:20
 * @Version 1.0
 */
@Service
public class UserService {
    private final ConcurrentHashMap<String, User> onlineUsers = new ConcurrentHashMap();
    private final List<String> stringSet = new ArrayList();

    public UserService() {
    }

    public void login(User user) {
        this.onlineUsers.put(user.getUsername(), user);
    }

    public void logout(String username) {
        this.stringSet.remove(username);
    }

    public int getOnlineUserCount() {
        return this.stringSet.size();
    }

    public void User(String username) {
        this.stringSet.add(username);
    }

    public List getList() {
        return this.stringSet;
    }
}

