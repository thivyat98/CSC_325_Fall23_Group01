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


public class searchController {
    // Replace the local list with a Firestore collection reference
    private final CollectionReference jobCollection;
    @FXML
    public TextField searchField;
    @FXML
    public VBox jobListingsContainer;


    public searchController() {
        // Assuming 'jobs' is the name of your Firestore collection
        this.jobCollection = FirestoreClient.getFirestore().collection("jobs");
    }

    public void onLoad() throws ExecutionException, InterruptedException {
        List<JobPosting> recent = fetchJobs("", 10); // Fetch recent jobs with a limit of 10
        displayJobs(recent);
    }

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


//    public void onLoad() throws ExecutionException, InterruptedException {
//        List<JobPosting> recent = fetchRecentJobs();
//        displayJobs(recent);
//    }
//
//    public void searchJobs(ActionEvent actionEvent) {
//        String searchTerm = searchField.getText().toLowerCase();
//        jobListingsContainer.getChildren().clear();
//
//        // Split the input string into multiple keywords using space as a separator
//        String[] keywords = searchTerm.split("\\s+");
//
//        for (String keyword : keywords) {
//            List<JobPosting> jobs = fetchJobs(keyword);
//            for (JobPosting job : jobs) {
//                HBox jobUI = createJobListingUI(job);
//                jobListingsContainer.getChildren().add(jobUI);
//            }
//        }
//    }


//    public List<JobPosting> fetchJobs(String keyword) {
//        List<JobPosting> jobs = new ArrayList<>();
//        List<String> pulledJobIds = new ArrayList<>(); // Use a List to store pulled job IDs
//
//        try {
//            // Query Firestore for jobs using the keyword
//            ApiFuture<QuerySnapshot> future = jobCollection.whereArrayContains("keywords", keyword).get();
//            QuerySnapshot querySnapshot = future.get();
//
//            // Display the matching jobs
//            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
//                JobPosting job = document.toObject(JobPosting.class);
//                if (job != null) {
//                    String jobId = job.getId();
//
//                    // Check if the job ID has already been pulled
//                    if (!pulledJobIds.contains(jobId)) {
//                        jobs.add(job);
//                        pulledJobIds.add(jobId);
//                    }
//                }
//            }
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        return jobs;
//    }


    public HBox createJobListingUI(JobPosting job) {
        HBox jobBox = new HBox(10);
        jobBox.getStyleClass().add("job-listing");

        VBox detailsBox = new VBox(5);

        Label titleLabel = new Label(job.getTitle()); // Assuming getTitle() is defined in Post
        titleLabel.getStyleClass().add("job-title");

        Label companyNameLabel = new Label(job.getCompany());
        companyNameLabel.getStyleClass().add("company-name");

        Label locationLabel = new Label(job.getLocation()); // Replace with actual location if available
        locationLabel.getStyleClass().add("job-location");

        Label salaryLabel = new Label("Salary: " + job.getSalary());
        salaryLabel.getStyleClass().add("job-description");

        Label descriptionLabel = new Label(job.getDescription()); // Assuming getDescription() is defined in Post
        descriptionLabel.getStyleClass().add("job-description");

        Button applyButton = new Button("Apply");
        applyButton.getStyleClass().add("button");
        applyButton.setOnAction(event -> {
            try {
                handleApplyAction(job);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        detailsBox.getChildren().addAll(titleLabel, companyNameLabel, locationLabel, salaryLabel, descriptionLabel, applyButton);
        jobBox.getChildren().add(detailsBox);

        return jobBox;
    }


    private void handleApplyAction(JobPosting job) throws IOException {
        User user = UserSessionManager.getUser();
        if (user instanceof Employee e) {
            job.addApplicants(e);
        }
        SceneManager.getInstance().showSuccessfulRegScene();
    }

//    public List<JobPosting> fetchRecentJobs() throws ExecutionException, InterruptedException {
//
    // Assuming 'jobs' is your collection and 'postedDate' is the timestamp field
//        ApiFuture<QuerySnapshot> query = jobCollection
//                .orderBy("unixTime", Query.Direction.DESCENDING)
//                .limit(10)
//                .get();
//
//        List<QueryDocumentSnapshot> documents = query.get().getDocuments();
//        List<JobPosting> recentJobs = new ArrayList<>();
//        for (QueryDocumentSnapshot document : documents) {
//            recentJobs.add(document.toObject(JobPosting.class));
//        }
//        return recentJobs;
//    }

    public void ProfileLoader(ActionEvent actionEvent) throws IOException {
        if (Objects.equals(UserSessionManager.getUser().getType(), "employer")) {
            SceneManager.getInstance().showEmployerProfileScene();
        } else if (Objects.equals(UserSessionManager.getUser().getType(), "employee"))
            SceneManager.getInstance().showEmployeeProfileScene();
    }

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
