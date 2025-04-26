package com.example.register2;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple in-memory database for user information.
 */
public class UserDatabase {
    // Storage for users, with email as the key
    private final Map<String, User> users = new HashMap<>();

    /**
     * Adds a new user to the database if the email doesn't already exist.
     *
     * @param user The User object to add.
     * @return true if the user was added successfully, false if the email already exists.
     */
    public boolean addUser(User user) {
        // Check if the user already exists
        if (users.containsKey(user.getEmail())) {
            return false; // User already exists
        }

        // Add the user to the database
        users.put(user.getEmail(), user);
        return true; // User added successfully
    }

    /**
     * Validates a user's credentials.
     *
     * @param email    The email to check.
     * @param password The password to validate.
     * @return true if the credentials are valid, false otherwise.
     */
    public boolean validateUser(String email, String password) {
        // Check if the user exists
        User user = users.get(email);
        if (user == null) {
            return false; // User doesn't exist
        }

        // Validate the password
        return user.getPassword().equals(password);
    }
    public boolean validateEmail(String email){
        User user = users.get(email);
        return user != null;
    }
    public String getPasswordByEmail(String email){
        User user = users.get(email);
        if (user == null) {
            return ""; // User doesn't exist
        }
        return user.getPassword();
    }

    /**
     * Gets a user by their email.
     *
     * @param email The email of the user to retrieve.
     * @return The User object, or null if not found.
     */
    public User getUser(String email) {
        return users.get(email);
    }
}