package com.example.csc325.csc325.users;

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
 * Represents an Employee user in the system.
 */
public class Employee extends User {
    private ArrayList<String> skills;
    private String firstName;
    private String lastName;

    /**
     * Default constructor for the Employee class.
     */
    public Employee() {
        super();
        this.firstName = "";
        this.lastName = "";
        this.skills = new ArrayList<>();
    }

    /**
     * Constructor for creating an Employee with specified attributes.
     *
     * @param firstName The first name of the employee.
     * @param lastName  The last name of the employee.
     * @param phone     The phone number of the employee.
     * @param email     The email of the employee.
     */
    public Employee(String firstName, String lastName, String phone, String email) {
        super(email, phone, "employee");
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = new ArrayList<>();
    }

    /**
     * Constructor for creating an Employee with specified attributes.
     *
     * @param firstName The first name of the employee.
     * @param lastName  The last name of the employee.
     * @param phone     The phone number of the employee.
     * @param email     The email of the employee.
     * @param id        The unique identifier of the employee.
     */
    public Employee(String firstName, String lastName, String phone, String email, String id) {
        super(email, id, phone, "employee");
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = new ArrayList<>();
    }

    /**
     * Retrieves an Employee instance based on the provided ID.
     *
     * @param ID The unique identifier of the employee.
     * @return An Employee instance if found, otherwise null.
     * @throws ExecutionException   If the Firestore operation encounters an exception.
     * @throws InterruptedException If the Firestore operation is interrupted.
     */
    public static Employee getEmployee(String ID) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users").document(ID);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return document.toObject(Employee.class);
        } else {
            System.out.println("No User Found!");
            return null;
        }
    }

    /**
     * Gets the skills of the employee.
     *
     * @return The list of skills.
     */
    public ArrayList<String> getSkills() {
        return skills;
    }

    /**
     * Sets the skills of the employee.
     *
     * @param skills The list of skills to set.
     */
    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    /**
     * Adds a skill to the employee's skill set and saves the changes.
     *
     * @param skill The skill to add.
     * @throws ExecutionException   If the Firestore operation encounters an exception.
     * @throws InterruptedException If the Firestore operation is interrupted.
     */
    public void addSkill(String skill) throws ExecutionException, InterruptedException {
        this.skills.add(skill);
        this.save();
    }

    /**
     * Removes a skill from the employee's skill set and saves the changes.
     *
     * @param skill The skill to remove.
     * @throws ExecutionException   If the Firestore operation encounters an exception.
     * @throws InterruptedException If the Firestore operation is interrupted.
     */
    public void removeSkill(String skill) throws ExecutionException, InterruptedException {
        this.skills.remove(skill);
        this.save();
    }

    /**
     * Saves the employee data to Firestore.
     *
     * @throws ExecutionException   If the Firestore operation encounters an exception.
     * @throws InterruptedException If the Firestore operation is interrupted.
     */
    @Override
    public void save() throws ExecutionException, InterruptedException {
        Map<String, Object> data = new HashMap<>();
        data.put("type", this.getType());
        data.put("firstName", this.getFirstName());
        data.put("lastName", this.getLastName());
        data.put("id", this.getId());
        data.put("email", super.getEmail());
        data.put("skills", this.getSkills());
        data.put("phone", this.getPhone());
        data.put("about", this.getAbout());
        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("users").document(super.getId());
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Update time : " + result.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        UserSessionManager.setCurrentUser(this.getId(), "employee");
    }

    /**
     * Registers the employee with the provided password and saves the changes.
     *
     * @param password The password for registration.
     * @throws ExecutionException   If the Firestore operation encounters an exception.
     * @throws InterruptedException If the Firestore operation is interrupted.
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
     * Gets the first name of the employee.
     *
     * @return The first name of the employee.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the employee.
     *
     * @param firstName The new first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the employee.
     *
     * @return The last name of the employee.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the employee.
     *
     * @param lastName The new last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
