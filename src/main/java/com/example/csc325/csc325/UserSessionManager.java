package com.example.csc325.csc325;

import com.example.csc325.csc325.users.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserSessionManager {
    private static String currentUserId = null;
    private static User user = null;

    public static void loginUser(String userId) {
        currentUserId = userId; // Set when user logs in
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

    public static void setUser(String currentUserId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users").document(currentUserId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            if(document.getString("Type").equals("Employee")){

            }
            System.out.println("Document data: " + document.getData());
        } else {
            System.out.println("No User Found!");
        }
    }
}
