package com.example.csc325.csc325.users;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Employee extends User {
    private UUID employeeId;
    private String jobTitle;
    private ArrayList<String> skills;

    public Employee(String userId, String name, String jobTitle) {
        super(userId, name);
        this.employeeId = UUID.randomUUID();
        this.jobTitle = jobTitle;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }
    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    @Override
    public String getUserType() {
        return "Employee";
    }

    @Override
    public void save(){
        Map<String, Object> data = new HashMap<>();
        data.put("Name", super.getName());
        data.put("EmployeeID", this.employeeId);
        data.put("Email", super.getEmail());
        data.put("Skills", this.skills);
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
