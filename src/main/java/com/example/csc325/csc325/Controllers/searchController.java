package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.Posts.JobPosting;
import com.example.csc325.csc325.SceneManager;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class searchController {
    @FXML
    public TextField searchField;
    public VBox jobListingsContainer;

    // Replace the local list with a Firestore collection reference
    private CollectionReference jobCollection;


    public searchController() {
        // Assuming 'jobs' is the name of your Firestore collection
        this.jobCollection = FirestoreClient.getFirestore().collection("jobs");
    }

    public void onLoad() throws ExecutionException, InterruptedException {
        List<JobPosting> recent = fetchRecentJobs();
        for (JobPosting job : recent) {
            HBox jobUI = createJobListingUI(job);
            jobListingsContainer.getChildren().add(jobUI);
        }
    }

    //ToDo: Implement search functionality for jobs with a set of keywords
    public void searchJobs(ActionEvent actionEvent) {
        String searchTerm = searchField.getText().toLowerCase();
        jobListingsContainer.getChildren().clear();
        ArrayList<JobPosting> jobs = fetchJobs(searchTerm);
        for (JobPosting job : jobs) {
            HBox jobUI = createJobListingUI(job);
            jobListingsContainer.getChildren().add(jobUI);
        }
    }

    public ArrayList<JobPosting> fetchJobs(String searchTerm){
        ArrayList<JobPosting> jobs = new ArrayList<>();
        try {
            // Query Firestore for jobs
            ApiFuture<QuerySnapshot> future = jobCollection.whereArrayContains("keywords", searchTerm).get();
            QuerySnapshot querySnapshot = future.get();

            // Display the matching jobs
            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                JobPosting job = document.toObject(JobPosting.class);
                if (job != null) {
                    jobs.add(job);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return jobs;
    }

    public HBox createJobListingUI(JobPosting job) {
        HBox jobBox = new HBox(10);
        jobBox.getStyleClass().add("job-listing");

        VBox detailsBox = new VBox(5);

        Label titleLabel = new Label(job.getTitle()); // Assuming getTitle() is defined in Post
        titleLabel.getStyleClass().add("job-title");

        Label companyNameLabel = new Label(job.getCompany());
        companyNameLabel.getStyleClass().add("company-name");

        Label locationLabel = new Label("Location: Unknown"); // Replace with actual location if available
        locationLabel.getStyleClass().add("job-location");

        Label salaryLabel = new Label("Salary: " + job.getSalary());
        salaryLabel.getStyleClass().add("job-description");

        Label descriptionLabel = new Label(job.getDescription()); // Assuming getDescription() is defined in Post
        descriptionLabel.getStyleClass().add("job-description");

        Button applyButton = new Button("Apply");
        applyButton.getStyleClass().add("apply-button");
        applyButton.setOnAction(event -> {
            try {
                handleApplyAction(job);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }); // Set up your event handler

        detailsBox.getChildren().addAll(titleLabel, companyNameLabel, locationLabel, salaryLabel, descriptionLabel, applyButton);
        jobBox.getChildren().add(detailsBox);

        return jobBox;
    }


    private void handleApplyAction(JobPosting job) throws IOException {
        // Handling apply action, e.g., printing job ID
        System.out.println("Applied for: " + job.getId()); // Assuming getId() is a method in Post or JobPosting
        SceneManager.getInstance().showSuccessfulRegScene();
    }

    public List<JobPosting> fetchRecentJobs() throws ExecutionException, InterruptedException {

        // Assuming 'jobs' is your collection and 'postedDate' is the timestamp field
        ApiFuture<QuerySnapshot> query = jobCollection
                .orderBy("unixTime", Query.Direction.DESCENDING)
                .limit(10)
                .get();

        List<QueryDocumentSnapshot> documents = query.get().getDocuments();
        List<JobPosting> recentJobs = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            recentJobs.add(document.toObject(JobPosting.class));
        }
        return recentJobs;
    }

}
