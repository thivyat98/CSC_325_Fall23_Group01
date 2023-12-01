package com.example.csc325.csc325.users;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import java.util.HashMap;
import java.util.Map;

public class Employer extends User {
    private String employerId;
    private String companyName;

    public Employer(String userId, String name, String employerId, String companyName) {
        super(userId, name);
        this.employerId = employerId;
        this.companyName = companyName;
    }

    public String getEmployerId() {
        return employerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String getUserType() {
        return "Employer";
    }

    @Override
    public void save(){
        Map<String, Object> data = new HashMap<>();
        data.put("Name", super.getName());
        data.put("EmployeeID", this.employerId);
        data.put("Email", super.getEmail());
        data.put("Company Name", this.companyName);
        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("users").document(super.getId());
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Update time : " + result.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
