package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.users.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for handling the logic of the employer listings view.
 */
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
    @FXML
    private TextField CompanyName;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNumber;

    // Constructor to initialize jobList
    public employerListingsController() {
        jobList = new ArrayList<>();
        // Populate jobList with sample jobs
        jobList.add(new Job("Software Developer", "Gaymen", "100,000€", "Make code", new String[]{"Tech", "Dev", "Software"}));
        // Add more jobs as needed
    }

    /**
     * Handles the event of adding a job to the job list.
     *
     * @param event The MouseEvent triggering the method.
     */
    @FXML
    void addJobToList(MouseEvent event) {
        jobListView.getItems().add(jobTitle.getText());
    }

    /**
     * Handles the event of removing a job from the job list.
     *
     * @param event The MouseEvent triggering the method.
     */
    @FXML
    void removeJobFromList(MouseEvent event) {
        int selectedID = jobListView.getSelectionModel().getSelectedIndex();
        jobListView.getItems().remove(selectedID);
    }

    /**
     * Shows the employer logo. (To be implemented)
     */
    @FXML
    protected void showEmployerLogo() {
        // Implement code to show employer logo
    }

    /**
     * Sets the employer's information for display.
     *
     * @param employer The User object representing the employer.
     */
    public void setEmployer(User employer) {
        this.employerName.setText(employer.getId());
    }

    /**
     * Implements search functionality for jobs with a set of keywords.
     *
     * @param event The MouseEvent triggering the method.
     */
    public void searchJobs(MouseEvent event) {
        String searchTerm = jobTitle.getText().toLowerCase();

        for (Job job : jobList) {
            if (containsKeyword(job, searchTerm)) {
                System.out.println("Job Matches " + job.jobTitle);
            }
        }
    }

    /**
     * Checks if a job contains the specified keyword.
     *
     * @param job        The Job object to check.
     * @param searchTerm The keyword to search for.
     * @return True if the job contains the keyword, false otherwise.
     */
    private boolean containsKeyword(Job job, String searchTerm) {
        for (String keyword : job.keywords) {
            if (keyword.toLowerCase().contains(searchTerm)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Represents a job with its details.
     */
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
}
