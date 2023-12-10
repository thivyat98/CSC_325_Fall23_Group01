package com.example.csc325.csc325;

import com.example.csc325.csc325.users.Employee;
import com.example.csc325.csc325.users.Employer;
import com.example.csc325.csc325.users.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserSessionManager {
    private static String currentUserId = null;
    private static User user = null;

    public static void loginUser(String userId, String type) throws ExecutionException, InterruptedException {
        currentUserId = userId;
        setCurrentUser(currentUserId, type);// Set when user logs in
    }

    public static void logoutUser() {
        currentUserId = null; // Clear when user logs out
    }

    public static String getCurrentUserId() {
        return currentUserId; // Access current user ID
    }

    public static User getUser() {
        return user;
    }

    public static void setCurrentUser(String currentUserId, String type) throws ExecutionException, InterruptedException {
        if(type.equalsIgnoreCase("employee")){
            user = Employee.getEmployee(currentUserId);
        }
        else if(type.equalsIgnoreCase("employer"))
        user = Employer.getEmployer(currentUserId);

    }
}
