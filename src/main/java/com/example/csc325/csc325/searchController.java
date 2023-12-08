package com.example.csc325.csc325;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class searchController {
    @FXML
    public TextField searchField;

    private List<Job> jobList;

    // Constructor to initialize jobList
    public searchController() {
        jobList = new ArrayList<>();
        // Populate jobList with sample jobs
        jobList.add(new Job("Software Developer", "Gaymen", "100,000€", "Make code", new String[]{"Tech", "Dev", "Software"}));
        // Add more jobs as needed
    }

    private static class Job {
        String jobTitle;
        String company;
        String salary;
        String description;
        String[] keywords;

        public Job(String jobTitle, String company, String salary, String description, String[] keywords) {
            this.jobTitle = jobTitle;
            this.company = company;
            this.salary = salary;
            this.description = description;
            this.keywords = keywords;
        }

    }

    //ToDo: Implement search functionality for jobs with a set of keywords
    public void searchJobs(ActionEvent actionEvent) {
        String searchTerm = searchField.getText().toLowerCase();

        for(Job job : jobList) {
            if(containsKeyword(job, searchTerm)) {
                System.out.println("Job Matches " + job.jobTitle);
            }
        }
    }

    private boolean containsKeyword(Job job, String searchTerm) {
        for(String keyword : job.keywords) {
            if(keyword.toLowerCase().contains(searchTerm)) {
                return true;
            }
        }
        return false;
    }
}
