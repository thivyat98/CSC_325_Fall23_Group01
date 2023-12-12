package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.Posts.JobPosting;
import com.example.csc325.csc325.SceneManager;
import com.example.csc325.csc325.UserSessionManager;
import com.example.csc325.csc325.users.Employee;
import com.example.csc325.csc325.users.Employer;
import com.example.csc325.csc325.users.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class applicantsController {

    public VBox applicantsContainer;

    public List<JobPosting> fetchjobs() {
        User user = UserSessionManager.getUser();
        Firestore db = FirestoreClient.getFirestore();
        List<JobPosting> jobPostings = new ArrayList<>();

        if (user instanceof Employer employer) {
            for (String id : employer.getPostedJobs()) {
                try {
                    // Fetch the document and convert it to a JobPosting object
                    DocumentSnapshot document = db.collection("jobs").document(id).get().get();
                    if (document.exists()) {
                        JobPosting jobPosting = document.toObject(JobPosting.class);
                        jobPostings.add(jobPosting);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace(); // Handle exceptions appropriately
                }
            }
        }
        return jobPostings;
    }

    public HBox createApplicantUI(Employee applicant) {
        HBox jobBox = new HBox(10);
        jobBox.getStyleClass().add("applicant-box");

        VBox detailsBox = new VBox(5);

        Label applicantName = new Label(applicant.getFirstName() + " " + applicant.getLastName());
        applicantName.getStyleClass().add("applicant-name");


        Label applicantEmail = new Label(applicant.getEmail());
        applicantEmail.getStyleClass().add("applicant-email");

        Label applicantPhone = new Label(applicant.getPhone());
        applicantPhone.getStyleClass().add("applicant-phone");


        Label applicantSkills = new Label("Skills: " + applicant.getSkills());
        applicantSkills.getStyleClass().add("applicant-skills");

        Label applicantAbout = new Label(applicant.getAbout()); // Assuming getDescription() is defined in Post
        applicantAbout.getStyleClass().add("applicant-aboutn");


        detailsBox.getChildren().addAll(applicantName, applicantEmail, applicantPhone, applicantSkills, applicantAbout);
        jobBox.getChildren().add(detailsBox);

        return jobBox;
    }

    public void displayApplicants(List<JobPosting> jobs) {
        VBox mainContainer = new VBox(10); // Main container for all jobs and their applicants

        for (JobPosting job : jobs) {
            // Create a label for the job
            Label jobLabel = new Label(job.getTitle());
            jobLabel.getStyleClass().add("job-title-label");

            // Container for applicants of this job
            VBox jobApplicantsContainer = new VBox(5);
            jobApplicantsContainer.getChildren().add(jobLabel);

            // Add applicants for this job
            for (Employee applicant : job.getApplicants()) {
                HBox applicantUI = createApplicantUI(applicant);
                jobApplicantsContainer.getChildren().add(applicantUI);
            }

            // Add the job and its applicants to the main container
            mainContainer.getChildren().add(jobApplicantsContainer);
        }

        applicantsContainer.getChildren().add(mainContainer);
    }

    public void onLoad(){
        List<JobPosting> jobs = fetchjobs();
        displayApplicants(jobs);

    }


    public void ProfileLoader(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showEmployerProfileScene();
    }
}

