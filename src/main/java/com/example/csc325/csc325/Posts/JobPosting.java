package com.example.csc325.csc325.Posts;

import com.example.csc325.csc325.users.Employee;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a job posting in the system.
 */
public class JobPosting extends Post {

    private String company;
    private String companyId;
    private String salary;
    private List<String> keywords;
    private List<Employee> applicants;
    private String location;

    /**
     * Constructor for creating a job posting with specified details.
     *
     * @param jobTitle    The title of the job posting.
     * @param company     The name of the company offering the job.
     * @param salary      The salary associated with the job.
     * @param description A description of the job.
     * @param keywords    A list of keywords associated with the job.
     * @param companyId   The unique identifier of the company offering the job.
     * @param location    The location of the job.
     */
    public JobPosting(String jobTitle, String company, String salary, String description, List<String> keywords, String companyId, String location) {
        super(jobTitle, description);
        this.company = company;
        this.salary = salary;
        this.keywords = keywords;
        this.applicants = new ArrayList<>();
        this.companyId = companyId;
        this.location = location;
    }

    /**
     * Default constructor for the JobPosting class.
     * Initializes job posting attributes with default values.
     */
    public JobPosting() {
        super();
        this.company = "";
        this.salary = "";
        this.keywords = new ArrayList<>();
        this.applicants = new ArrayList<>();
        this.companyId = "";
        this.location = "";
    }

    /**
     * Checks for and removes duplicate job postings from the provided list.
     *
     * @param jobs The list of job postings to check for duplicates.
     * @return A list of non-duplicate job postings.
     */
    public static List<JobPosting> checkDupes(List<JobPosting> jobs) {
        Set<JobPosting> uniqueJobs = new HashSet<>();
        List<JobPosting> nonDuplicateJobs = new ArrayList<>();

        for (JobPosting job : jobs) {
            if (uniqueJobs.add(job)) {
                // If job is added successfully, it's not a duplicate.
                nonDuplicateJobs.add(job);
            }
        }

        return nonDuplicateJobs;
    }

    /**
     * Gets the name of the company offering the job.
     *
     * @return The name of the company.
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the name of the company offering the job.
     *
     * @param company The new name of the company.
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Gets the salary associated with the job.
     *
     * @return The salary of the job.
     */
    public String getSalary() {
        return salary;
    }

    /**
     * Sets the salary associated with the job.
     *
     * @param salary The new salary for the job.
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /**
     * Gets the list of keywords associated with the job.
     *
     * @return The list of keywords.
     */
    public List<String> getKeywords() {
        return keywords;
    }

    /**
     * Sets the list of keywords associated with the job.
     *
     * @param keywords The new list of keywords.
     */
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Gets the list of employees who have applied for the job.
     *
     * @return The list of applicants.
     */
    public List<Employee> getApplicants() {
        return applicants;
    }

    /**
     * Sets the list of employees who have applied for the job.
     *
     * @param applicants The new list of applicants.
     */
    public void setApplicants(List<Employee> applicants) {
        this.applicants = applicants;
    }

    /**
     * Adds an applicant to the list of employees who have applied for the job.
     *
     * @param e The employee to be added as an applicant.
     */
    public void addApplicants(Employee e) {
        this.applicants.add(e);
        this.save();
    }

    /**
     * Saves the job posting data to Firestore.
     */
    @Override
    public void save() {
        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("jobs").document(this.getId());
            ApiFuture<WriteResult> result = docRef.set(this);
            System.out.println("Update time : " + result.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Gets the unique identifier of the company offering the job.
     *
     * @return The company ID.
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * Sets the unique identifier of the company offering the job.
     *
     * @param companyId The new company ID.
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * Gets the location of the job.
     *
     * @return The location of the job.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the job.
     *
     * @param location The new location of the job.
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
