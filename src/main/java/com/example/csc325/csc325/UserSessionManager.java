package com.example.csc325.csc325;

import com.example.csc325.csc325.users.Employee;
import com.example.csc325.csc325.users.Employer;
import com.example.csc325.csc325.users.User;

import java.util.concurrent.ExecutionException;

/**
 * Manages the user session, including login, logout, and access to the current user's information.
 */
public class UserSessionManager {
    private static String currentUserId = null;
    private static User user = null;

    /**
     * Logs in a user and sets the current user based on the provided user ID and user type.
     *
     * @param userId The ID of the user to log in.
     * @param type   The type of the user (employee or employer).
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
    public static void loginUser(String userId, String type) throws ExecutionException, InterruptedException {
        currentUserId = userId;
        setCurrentUser(currentUserId, type); // Set when the user logs in
    }

    /**
     * Logs out the current user by clearing the current user ID and user information.
     */
    public static void logoutUser() {
        currentUserId = null; // Clear when the user logs out
        user = null;
    }

    /**
     * Gets the ID of the current logged-in user.
     *
     * @return The current user ID.
     */
    public static String getCurrentUserId() {
        return currentUserId; // Access the current user ID
    }

    /**
     * Gets the current logged-in user.
     *
     * @return The current user.
     */
    public static User getUser() {
        return user;
    }

    /**
     * Sets the current user based on the provided user ID and type.
     *
     * @param currentUserId The ID of the user to set as the current user.
     * @param type          The type of the user (employee or employer).
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
    public static void setCurrentUser(String currentUserId, String type) throws ExecutionException, InterruptedException {
        if (type.equalsIgnoreCase("employee")) {
            user = Employee.getEmployee(currentUserId);
        } else if (type.equalsIgnoreCase("employer")) {
            user = Employer.getEmployer(currentUserId);
        }
    }
}
