package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.Posts.JobPosting;
import com.example.csc325.csc325.SceneManager;
import com.example.csc325.csc325.UserSessionManager;
import com.example.csc325.csc325.users.Employee;
import com.example.csc325.csc325.users.Employer;
import com.example.csc325.csc325.users.User;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Controller class for handling the logic of the applicants view.
 */
public class applicantsController {

    /**
     * Container for displaying the list of applicants.
     */
    public VBox applicantsContainer;

    /**
     * Fetches the jobs posted by the currently logged-in employer.
     *
     * @return List of JobPosting objects representing the posted jobs.
     */
    public List<JobPosting> fetchJobs() {
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

    /**
     * Creates the UI representation for a specific applicant.
     *
     * @param applicant The Employee object representing the applicant.
     * @return HBox containing the UI representation of the applicant.
     */
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

        Label applicantAbout = new Label(applicant.getAbout());
        applicantAbout.getStyleClass().add("applicant-about");

        detailsBox.getChildren().addAll(applicantName, applicantEmail, applicantPhone, applicantSkills, applicantAbout);
        jobBox.getChildren().add(detailsBox);

        return jobBox;
    }

    /**
     * Displays the list of applicants for each job in the given list of JobPosting objects.
     *
     * @param jobs List of JobPosting objects.
     */
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
                Separator separator = new Separator();
                separator.getStyleClass().add("separator");
                jobApplicantsContainer.getChildren().add(separator);
            }

            // Add the job and its applicants to the main container
            mainContainer.getChildren().add(jobApplicantsContainer);
        }

        applicantsContainer.getChildren().add(mainContainer);
    }

    /**
     * Initializes the view by fetching jobs and displaying applicants.
     */
    public void onLoad() {
        List<JobPosting> jobs = fetchJobs();
        displayApplicants(jobs);
    }

    /**
     * Handles the action event for navigating to the employer's profile.
     *
     * @param actionEvent The ActionEvent triggering the method.
     * @throws IOException If an I/O exception occurs.
     */
    public void profileLoader(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showEmployerProfileScene();
    }
}
