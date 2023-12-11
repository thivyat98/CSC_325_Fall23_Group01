package com.example.csc325.csc325.Posts;

import com.example.csc325.csc325.users.Employee;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import java.util.*;

public class JobPosting extends Post {

    private String company;

    private String companyId;
    private String salary;
    private List<String> keywords;

    private List<Employee> applicants;

    private String location;

    public JobPosting() {
        super();
        this.company = "";
        this.salary = "";
        this.keywords = new ArrayList<>();
        this.applicants = new ArrayList<>();
        this.companyId = "";
        this.location="";

    }

    public JobPosting(String jobTitle, String company, String salary, String description, List<String> keywords, String companyId, String location) {
        super(jobTitle, description);
        this.company = company;
        this.salary = salary;
        this.keywords = keywords;
        this.applicants = new ArrayList<>();
        this.companyId = companyId;
        this.location = location;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public static List<JobPosting> checkDupes(List<JobPosting> jobs){
        Set<JobPosting> uniqueJobs = new HashSet<>();
        List<JobPosting> nonDuplicateJobs = new ArrayList<>();

        for(JobPosting job : jobs){
            if(uniqueJobs.add(job)){
                System.out.println("0");
                // If job is added successfully, it's not a duplicate.
                nonDuplicateJobs.add(job);
            }
        }

        return nonDuplicateJobs;
    }

    public List<Employee> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<Employee> applicants) {
        this.applicants = applicants;
    }

    public void addApplicants(Employee e){
        this.applicants.add(e);
        this.save();
    }

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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
