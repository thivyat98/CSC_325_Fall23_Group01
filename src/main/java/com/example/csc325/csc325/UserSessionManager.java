package com.example.csc325.csc325;

public class UserSessionManager {
    private static String currentUserId = null;

    public static void loginUser(String userId) {
        currentUserId = userId; // Set when user logs in
    }

    public static void logoutUser() {
        currentUserId = null; // Clear when user logs out
    }

    public static String getCurrentUserId() {
        return currentUserId; // Access current user ID
    }
}
