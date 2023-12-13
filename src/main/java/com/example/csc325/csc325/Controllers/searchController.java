package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.Posts.JobPosting;
import com.example.csc325.csc325.SceneManager;
import com.example.csc325.csc325.UserSessionManager;
import com.example.csc325.csc325.users.Employee;
import com.example.csc325.csc325.users.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Controller class for handling the logic of the job search view.
 */
public class searchController {
    // Replace the local list with a Firestore collection reference
    private final CollectionReference jobCollection;

    @FXML
    public TextField searchField;
    @FXML
    public VBox jobListingsContainer;

    /**
     * Constructor for initializing the Firestore collection reference.
     */
    public searchController() {
        // Assuming 'jobs' is the name of your Firestore collection
        this.jobCollection = FirestoreClient.getFirestore().collection("jobs");
    }

    /**
     * Loads the recent job listings when the scene is loaded.
     *
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
    public void onLoad() throws ExecutionException, InterruptedException {
        List<JobPosting> recent = fetchJobs("", 10); // Fetch recent jobs with a limit of 10
        displayJobs(recent);
    }

    /**
     * Handles the search for jobs based on the entered keywords.
     *
     * @param actionEvent The event triggered by the search button.
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
    public void searchJobs(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        String searchTerm = searchField.getText().toLowerCase().trim();
        jobListingsContainer.getChildren().clear();

        // Split the cleaned input string into multiple keywords using space as a separator
        String[] keywords = searchTerm.split("\\s+");

        // Fetch jobs for each non-empty keyword
        List<JobPosting> allJobs = new ArrayList<>();
        for (String keyword : keywords) {
            if (!keyword.isEmpty()) {
                allJobs.addAll(fetchJobs(keyword, 10));// Adjust the limit as needed
            }
        }
        allJobs = JobPosting.checkDupes(allJobs);
        displayJobs(allJobs);
    }

    /**
     * Fetches jobs from Firestore based on the specified keyword and limit.
     *
     * @param keyword The keyword to search for in job listings.
     * @param limit   The maximum number of jobs to fetch.
     * @return A list of JobPosting objects matching the criteria.
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
    public List<JobPosting> fetchJobs(String keyword, int limit) throws ExecutionException, InterruptedException {
        List<JobPosting> jobs = new ArrayList<>();
        List<String> pulledJobIds = new ArrayList<>();

        if (keyword.isEmpty()) {
            ApiFuture<QuerySnapshot> query = jobCollection
                    .orderBy("unixTime", Query.Direction.DESCENDING)
                    .limit(limit)
                    .get();

            List<QueryDocumentSnapshot> documents = query.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                JobPosting job = document.toObject(JobPosting.class);
                if (job != null && !pulledJobIds.contains(job.getId())) {
                    jobs.add(job);
                    pulledJobIds.add(job.getId());
                }
            }
        } else {
            ApiFuture<QuerySnapshot> future = jobCollection
                    .whereArrayContains("keywords", keyword)
                    .limit(limit)
                    .get();

            QuerySnapshot querySnapshot = future.get();
            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                JobPosting job = document.toObject(JobPosting.class);
                if (job != null && !pulledJobIds.contains(job.getId())) {
                    jobs.add(job);
                    pulledJobIds.add(job.getId());
                }
            }
        }
        return jobs;
    }

    /**
     * Creates the user interface for displaying a job listing.
     *
     * @param job The JobPosting object for which to create the UI.
     * @return An HBox representing the job listing UI.
     */
    public HBox createJobListingUI(JobPosting job) {
        HBox jobBox = new HBox(10);
        jobBox.getStyleClass().add("job-listing");

        VBox detailsBox = new VBox(5);

        Label titleLabel = new Label(job.getTitle());
        titleLabel.getStyleClass().add("job-title");

        Label companyNameLabel = new Label(job.getCompany());
        companyNameLabel.getStyleClass().add("company-name");

        Label locationLabel = new Label(job.getLocation());
        locationLabel.getStyleClass().add("job-location");

        Label salaryLabel = new Label("Salary: " + job.getSalary());
        salaryLabel.getStyleClass().add("job-description");

        Label descriptionLabel = new Label(job.getDescription());
        descriptionLabel.getStyleClass().add("job-description");

        Button applyButton = new Button("Apply");
        applyButton.getStyleClass().add("button");
        applyButton.setOnAction(event -> {
            try {
                if (UserSessionManager.getUser().getType().equals("employee")) {
                    handleApplyAction(job);
                    applyButton.setVisible(false);
                    Label appliedLabel = new Label("Applied");
                    appliedLabel.getStyleClass().add("applied-label");
                    detailsBox.getChildren().add(appliedLabel);
                } else {
                    applyButton.setVisible(false);
                    Label appliedLabel = new Label("Business Accounts Cannot Apply");
                    appliedLabel.getStyleClass().add("error-label");
                    detailsBox.getChildren().add(appliedLabel);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        detailsBox.getChildren().addAll(titleLabel, companyNameLabel, locationLabel, salaryLabel, descriptionLabel, applyButton);
        jobBox.getChildren().add(detailsBox);

        return jobBox;
    }

    /**
     * Handles the action when a user applies for a job.
     *
     * @param job The JobPosting object for which the user is applying.
     * @throws IOException If an I/O error occurs.
     */
    private void handleApplyAction(JobPosting job) throws IOException {
        User user = UserSessionManager.getUser();
        if (user instanceof Employee e) {
            job.addApplicants(e);
        }
    }

    /**
     * Loads the user profile scene based on the user's type (employer or employee).
     *
     * @param actionEvent The event triggered by the "Profile" button.
     * @throws IOException If an I/O error occurs.
     */
    public void ProfileLoader(ActionEvent actionEvent) throws IOException {
        if (Objects.equals(UserSessionManager.getUser().getType(), "employer")) {
            SceneManager.getInstance().showEmployerProfileScene();
        } else if (Objects.equals(UserSessionManager.getUser().getType(), "employee"))
            SceneManager.getInstance().showEmployeeProfileScene();
    }

    /**
     * Displays the fetched job listings in the UI.
     *
     * @param jobs The list of JobPosting objects to display.
     */
    private void displayJobs(List<JobPosting> jobs) {
        for (JobPosting job : jobs) {
            HBox jobUI = createJobListingUI(job);
            jobListingsContainer.getChildren().add(jobUI);
            Separator separator = new Separator();
            separator.getStyleClass().add("separator");
            jobListingsContainer.getChildren().add(separator);
        }
    }
}
