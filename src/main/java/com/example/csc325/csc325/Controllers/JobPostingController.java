package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.Posts.JobPosting;
import com.example.csc325.csc325.SceneManager;
import com.example.csc325.csc325.UserSessionManager;
import com.example.csc325.csc325.users.Employer;
import com.example.csc325.csc325.users.User;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * Controller class for handling the posting of job listings by employers.
 */
public class JobPostingController {
    public TextField jobTitle;
    public TextField jobLocation;
    public TextField salary;
    public TextField keywords;
    public TextArea description;

    /**
     * Cancels the job posting and returns to the employer's profile scene.
     *
     * @param actionEvent The event triggered by the cancel button.
     * @throws IOException If an I/O error occurs.
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showEmployerProfileScene();
    }

    /**
     * Posts a job listing based on the entered information.
     *
     * @param actionEvent The event triggered by the post button.
     * @throws IOException          If an I/O error occurs.
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
    public void onPost(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        User user = UserSessionManager.getUser();

        // Check if the logged-in user is an employer
        if (user instanceof Employer employer) {
            // Retrieve job details from the input fields
            String jobTitle = this.jobTitle.getText();
            String jobLocation = this.jobLocation.getText();
            String salary = this.salary.getText();
            String[] keywords = this.keywords.getText().split("\\s+");
            String description = this.description.getText();
            String company = employer.getCompanyName();
            String companyId = user.getId();

            // Create a JobPosting object with the provided details
            JobPosting job = new JobPosting(jobTitle, company, salary, description, new ArrayList<>(Arrays.asList(keywords)), companyId, jobLocation);

            // Save the job listing to Firestore
            job.save();

            // Add the posted job to the employer's list of posted jobs
            employer.addPostedJob(job);

            // Save the updated employer information to Firestore
            employer.save();
        }

        // Switch to the main scene after posting the job
        SceneManager.getInstance().showMainScene();
    }
}
