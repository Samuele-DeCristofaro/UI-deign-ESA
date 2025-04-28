package com.esa.moviestar.bin;

public class User {
    // User attributes
    private final String email;
    private final String password;

    /**
     * Constructs a new User object with the specified email and password.
     *
     * @param email_    The user's email address.
     * @param password_ The user's password.
     */
    public User(String email_, String password_) {
        email = email_;
        password = password_;
    }

    /**
     * Retrieves the user's password.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the user's email (used as name in this context).
     *
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Checks if this User object is equal to another object.
     * com.esa.moviestar.Users are considered equal if their email addresses are the same.
     *
     * @param obj The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return email.equals(user.email);
    }

    /**
     * Generates a hash code for this User object based on the email.
     *
     * @return The hash code of the email.
     */
    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
