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

/**
 * Represents an employer user in the system.
 */
public class Employer extends User {
    private String companyName;
    private ArrayList<String> postedJobs;

    /**
     * Constructor for creating an employer with specified company name, email, and phone.
     *
     * @param companyName The name of the employer's company.
     * @param email       The email address of the employer.
     * @param phone       The phone number of the employer.
     */
    public Employer(String companyName, String email, String phone) {
        super(email, phone, "employer");
        this.companyName = companyName;
        this.postedJobs = new ArrayList<>();
    }

    /**
     * Constructor for creating an employer with specified company name, email, phone, and ID.
     *
     * @param companyName The name of the employer's company.
     * @param email       The email address of the employer.
     * @param phone       The phone number of the employer.
     * @param id          The unique identifier for the employer.
     */
    public Employer(String companyName, String email, String phone, String id) {
        super(email, phone, id, "employer");
        this.companyName = companyName;
        this.postedJobs = new ArrayList<>();
    }

    /**
     * Default constructor for the Employer class.
     * Initializes employer attributes with default values.
     */
    public Employer() {
        super();
        this.companyName = "";
        this.postedJobs = new ArrayList<>();
    }

    /**
     * Retrieves an employer user from Firestore using the provided ID.
     *
     * @param ID The unique identifier of the employer user.
     * @return The employer user retrieved from Firestore.
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
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

    /**
     * Saves the employer's data to Firestore.
     *
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
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

    /**
     * Registers the employer with a password and saves the data.
     *
     * @param password The password to register the employer.
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
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

    /**
     * Gets the list of posted job IDs by the employer.
     *
     * @return The list of posted job IDs.
     */
    public ArrayList<String> getPostedJobs() {
        return postedJobs;
    }

    /**
     * Sets the list of posted job IDs by the employer.
     *
     * @param postedJobs The list of posted job IDs.
     */
    public void setPostedJobs(ArrayList<String> postedJobs) {
        this.postedJobs = postedJobs;
    }

    /**
     * Adds a job to the list of posted jobs by the employer.
     *
     * @param job The job to be added.
     */
    public void addPostedJob(JobPosting job) {
        postedJobs.add(job.getId());
    }

    /**
     * Gets the name of the employer's company.
     *
     * @return The name of the employer's company.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the name of the employer's company.
     *
     * @param companyName The new name of the employer's company.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
