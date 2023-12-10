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

    private String firstName;
    private String lastName;

    public static Employee getEmployee(String ID) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users").document(ID);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        document.toObject(Employee.class);
        if (document.exists()) {
            String firstName = document.getString("First Name");
            String lastName = document.getString("Last Name");
            String email = document.getString("Email");
            String phone = document.getString("Phone");
            String id = document.getString("ID");
            ArrayList<String> skills = (ArrayList<String>) document.get("Skills");

            Employee employee = new Employee(firstName, lastName, phone, email, id);
            employee.setSkills(skills); // Assuming you have a setter for skills
            return employee;
        } else {
            System.out.println("No User Found!");
            return null;
        }
    }

    public Employee(String firstName, String lastName, String phone, String email) {
        super(email, phone, "Employee");
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(String firstName, String lastName, String phone, String email, String id) {
        super(email, id, phone, "Employee");
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }
    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }


    @Override
    public void save(){
        Map<String, Object> data = new HashMap<>();
        data.put("type", this.getType());
        data.put("firstName", this.getFirstName());
        data.put("lastName", this.getLastName());
        data.put("ID", this.getId());
        data.put("email", super.getEmail());
        data.put("skills", this.getSkills());
        data.put("phone", this.getPhone());
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
    public void register(String password) throws ExecutionException, InterruptedException {
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
