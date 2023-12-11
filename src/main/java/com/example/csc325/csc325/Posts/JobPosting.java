package com.example.csc325.csc325.Posts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JobPosting extends Post {

    private String company;
    private String salary;
    private List<String> keywords;

    public JobPosting() {
        super();
        this.company = "";
        this.salary = "";
        this.keywords = new ArrayList<>();
    }

    public JobPosting(String jobTitle, String company, String salary, String description, List<String> keywords) {
        super(jobTitle, description);
        this.company = company;
        this.salary = salary;
        this.keywords = keywords;
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
}
