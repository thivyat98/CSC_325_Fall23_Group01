package com.example.csc325.csc325.Posts;

import java.util.ArrayList;
import java.util.List;

public class JobPosting extends Post{

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
}
