package com.example.duhakahya697637endassignment;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private final List<User> users;

    public UserDatabase() {
        // Initialize the database with some sample users
        users = new ArrayList<>();
        users.add(new User("admin", "1234", "Sales"));
        users.add(new User("duha", "4321", "Manager"));
    }

    public boolean isValidUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Valid user
            }
        }
        return false; // User not found or invalid password
    }

    public String getUserRole(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user.getRole(); // Return the user's role
            }
        }
        return null; // User not found or invalid password
    }


    // Define a User class to store username and password
    private static class User {
        private final String username;
        private final String password;
        private final String role;

        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }
    }
}
