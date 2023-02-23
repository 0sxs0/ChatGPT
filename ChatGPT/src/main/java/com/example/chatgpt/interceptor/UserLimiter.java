package com.example.chatgpt.interceptor;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 *
 * @Author Jialing Sun
 * @Date 2023/2/15 18:20
 * @Version 1.0
 */
@Service
public class UserLimiter {
    private static final int MAX_ONLINE_USERS = 50;
    private static final ConcurrentHashMap<String, Boolean> ONLINE_USERS = new ConcurrentHashMap();
    private static final AtomicInteger COUNT = new AtomicInteger(0);

    public UserLimiter() {
    }

    public static boolean addUser(String user) {
        if (COUNT.get() >= 50) {
            return false;
        } else if (ONLINE_USERS.putIfAbsent(user, true) == null) {
            COUNT.incrementAndGet();
            return true;
        } else {
            return false;
        }
    }

    public static void removeUser(String user) {
        if (ONLINE_USERS.remove(user) != null) {
            COUNT.decrementAndGet();
        }

    }
}

