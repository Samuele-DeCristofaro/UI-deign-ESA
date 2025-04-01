package com.example.register2;
import com.example.register2.User;
import java.util.ArrayList;

public class UserDatabase {
    private ArrayList<User> users;

    public UserDatabase() {
        users = new ArrayList<>();
    }

    public boolean addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            return true;
        }
        return false;
    }

    public boolean containsUser(User user) {
        return users.contains(user);
    }
}