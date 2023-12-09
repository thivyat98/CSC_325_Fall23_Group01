package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.users.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class employerListingsController {
    @FXML
    private Label employerName;

    @FXML
    private ListView<String> jobListView;

    @FXML
    private TextField jobTitle;

    @FXML
    private Button addJob;

    private List<Job> jobList;

    // Constructor to initialize jobList
    public employerListingsController() {
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

    @FXML
    void addJobToList(MouseEvent event) {
        jobListView.getItems().add(jobTitle.getText());
    }

    @FXML
    void removeJobFromList(MouseEvent event) {
        int selectedID = jobListView.getSelectionModel().getSelectedIndex();
        jobListView.getItems().remove(selectedID);
    }

    @FXML
    protected void showEmployerLogo() {
        // Implement code to show employer logo
    }

    public void setEmployer(User employer) {
        this.employerName.setText(employer.getId());
    }

    // Implement search functionality for jobs with a set of keywords
    public void searchJobs(MouseEvent event) {
        String searchTerm = jobTitle.getText().toLowerCase();

        for (Job job : jobList) {
            if (containsKeyword(job, searchTerm)) {
                System.out.println("Job Matches " + job.jobTitle);
            }
        }
    }

    private boolean containsKeyword(Job job, String searchTerm) {
        for (String keyword : job.keywords) {
            if (keyword.toLowerCase().contains(searchTerm)) {
                return true;
            }
        }
        return false;
    }
}
