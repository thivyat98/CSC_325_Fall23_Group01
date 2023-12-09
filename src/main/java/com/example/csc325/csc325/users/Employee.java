package com.example.csc325.csc325.users;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Employee extends User {
    private ArrayList<String> skills;
    private String phone;

    private String firstName;
    private String lastName;

    public Employee(String firstName, String lastName, String phone, String password, String email) {
        super(password, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
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
        data.put("Type", "Employee");
        data.put("First Name", this.getFirstName());
        data.put("Last Name", this.getLastName());
        data.put("ID", this.getId());
        data.put("Email", super.getEmail());
        data.put("Skills", this.getSkills());
        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("users").document(super.getId());
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Update time : " + result.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @Override
    public void register() throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = firestore.collection("auth")
                .whereEqualTo("username", this.getEmail())
                .get();
        List<QueryDocumentSnapshot> documents = query.get().getDocuments();

        if (!documents.isEmpty()) {
            System.out.println("Account using email already exists");
            return;

        }
        try {
            // Add data to Firestore
            Map<String, Object> creds = new HashMap<>();
            creds.put("username", this.getEmail());
            creds.put("hashPassword", BCrypt.hashpw(this.getPassword(), BCrypt.gensalt()));
            creds.put("ID", this.getId());

            DocumentReference docRef = firestore.collection("auth").document(this.getId());
            ApiFuture<WriteResult> result = docRef.set(creds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.save();

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
