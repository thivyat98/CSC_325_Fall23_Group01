package com.example.csc325.csc325.users;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

public class Employer extends User {
    private String companyName;

    public Employer(String companyName, String password, String email) {
        super(password, email);
        this.companyName = companyName;
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
        data.put("Type", "Employer");
        data.put("ID", this.getId());
        data.put("Email", this.getEmail());
        data.put("Company Name", this.getCompanyName());
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
    public void register() {
        Firestore firestore = FirestoreClient.getFirestore();
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
    }
}
