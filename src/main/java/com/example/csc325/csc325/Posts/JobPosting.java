package com.example.csc325.csc325.Posts;

import java.util.ArrayList;
import java.util.List;

public class JobPosting {

    private String jobTitle;
    private String company;
    private String salary;
    private String description;
    private ArrayList<String> keywords;

    public JobPosting(String jobTitle, String company, String salary, String description, ArrayList<String> keywords) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.salary = salary;
        this.description = description;
        this.keywords = keywords;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }
}
