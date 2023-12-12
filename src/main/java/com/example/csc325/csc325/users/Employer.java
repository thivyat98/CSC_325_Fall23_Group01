package com.example.csc325.csc325.users;

import com.example.csc325.csc325.Posts.JobPosting;
import com.example.csc325.csc325.UserSessionManager;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Employer extends User {
    private String companyName;

    private ArrayList<String> postedJobs;

    public Employer(String companyName, String email, String phone) {
        super(email, phone, "employer");
        this.companyName = companyName;
        this.postedJobs = new ArrayList<>();
    }

    public Employer(String companyName, String email, String phone, String id) {
        super(email, phone, id, "employer");
        this.companyName = companyName;
        this.postedJobs = new ArrayList<>();
    }

    public Employer() {
        super();
        this.companyName = "";
        this.postedJobs = new ArrayList<>();
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


    @Override
    public void save() throws ExecutionException, InterruptedException {
        Map<String, Object> data = new HashMap<>();
        data.put("type", this.getType());
        data.put("id", this.getId());
        data.put("email", this.getEmail());
        data.put("companyName", this.getCompanyName());
        data.put("phone", this.getPhone());
        data.put("about", this.getAbout());
        data.put("postedJobs", this.getPostedJobs());
        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("users").document(super.getId());
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Update time : " + result.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        UserSessionManager.setCurrentUser(this.getId(), "employer");

    }

    @Override
    public void register(String password) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = firestore.collection("auth")
                .whereEqualTo("username", this.getEmail().toLowerCase())
                .get();
        List<QueryDocumentSnapshot> documents = query.get().getDocuments();

        if (!documents.isEmpty()) {
            System.out.println("Account using email already exists");
            return;

        }
        try {
            // Add data to Firestore
            Map<String, Object> creds = new HashMap<>();
            creds.put("username", this.getEmail().toLowerCase());
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

    public ArrayList<String> getPostedJobs() {
        return postedJobs;
    }

    public void setPostedJobs(ArrayList<String> postedJobs) {
        this.postedJobs = postedJobs;
    }

    public void addPostedJob(JobPosting job){
        postedJobs.add(job.getId());
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
