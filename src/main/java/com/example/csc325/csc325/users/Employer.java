package com.example.csc325.csc325.users;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Employer extends User {
    private String companyName;

    public Employer(String companyName, String email, String phone) {
        super(email, phone, "Employer");
        this.companyName = companyName;
    }

    public Employer(String companyName, String email, String phone, String id) {
        super(email, phone, id, "Employer");
        this.companyName = companyName;
    }

    public Employer() {
        super();
        this.companyName = "";
    }

    public static Employer getEmployer(String ID) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users").document(ID);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return document.toObject(Employer.class);
        } else {
            System.out.println("No User Found!");
            return null;
        }
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public void save() {
        Map<String, Object> data = new HashMap<>();
        data.put("Type", this.getType());
        data.put("ID", this.getId());
        data.put("Email", this.getEmail());
        data.put("Company Name", this.getCompanyName());
        data.put("Phone", this.getPhone());
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
    public void register(String password) {
        Firestore firestore = FirestoreClient.getFirestore();
        try {
            // Add data to Firestore
            Map<String, Object> creds = new HashMap<>();
            creds.put("username", this.getEmail());
            creds.put("hashPassword", BCrypt.hashpw(password, BCrypt.gensalt()));
            creds.put("ID", this.getId());
            creds.put("Type", this.getType());


            DocumentReference docRef = firestore.collection("auth").document(this.getId());
            ApiFuture<WriteResult> result = docRef.set(creds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.save();
    }

}
