package com.example.register2;

import java.util.ArrayList;

public class UserDatabase {
    private ArrayList<User> users;

    public UserDatabase() {
        users = new ArrayList<>();
    }

    /**
     * Adds a new user to the database if they don't already exist
     *
     * @param user The user to add
     * @return true if the user was added successfully, false if the user already exists
     */
    public boolean addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            return true;
        }
        return false;
    }

    /**
     * Checks if a user exists in the database
     *
     * @param user The user to check
     * @return true if the user exists, false otherwise
     */
    public boolean containsUser(User user) {
        return users.contains(user);
    }

    /**
     * Validates user credentials against the database
     *
     * @param email The email to validate
     * @param password The password to validate
     * @return true if credentials are valid, false otherwise
     */
    public boolean validateUser(String email, String password) {
        User userToValidate = new User(email, password);

        for (User user : users) {
            // Check if email matches
            if (user.getEmail().equals(email)) {
                // Check if password matches
                return user.getPassword().equals(password);
            }
        }

        // User not found or password doesn't match
        return false;
    }

    /**
     * Validates a user object against the database
     *
     * @param user The user object to validate
     * @return true if the user exists and passwords match, false otherwise
     */
    public boolean validateUser(User user) {
        // First check if the user exists
        if (!users.contains(user)) {
            return false;
        }

        // Then check if the passwords match
        for (User existingUser : users) {
            if (existingUser.getEmail().equals(user.getEmail())) {
                return existingUser.getPassword().equals(user.getPassword());
            }
        }

        return false;
    }
}