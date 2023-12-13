package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.SceneManager;
import com.example.csc325.csc325.UserSessionManager;
import com.example.csc325.csc325.users.Employer;
import com.example.csc325.csc325.users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Controller class for handling the logic of the employer profile view.
 */
public class employerProfileController {
    @FXML
    public Label username;
    @FXML
    public Label companyName;
    public TextArea about;
    public Button logOutBtn;

    @FXML
    private Label phoneNumber;

    @FXML
    private Button saveButton;

    /**
     * Initializes the employer profile view with user data.
     */
    public void onLoad() {
        User user = UserSessionManager.getUser();
        if (user instanceof Employer employer) {
            // Set the username label to the employer's email
            username.setText(employer.getEmail());
            // Set the companyName label to the employer's company name
            companyName.setText(employer.getCompanyName());
            phoneNumber.setText(employer.getPhone());
            about.setText(user.getAbout());
        }
    }

    /**
     * Saves the employer's profile information.
     *
     * @param actionEvent The ActionEvent triggering the method.
     * @throws IOException          If an I/O error occurs.
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
    public void saveProfile(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        User user = UserSessionManager.getUser();
        user.setAbout(about.getText());
        user.save();

        SceneManager.getInstance().showMainScene();
    }

    /**
     * Handles the event of logging out the employer.
     *
     * @param actionEvent The ActionEvent triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void logOutHandler(ActionEvent actionEvent) throws IOException {
        UserSessionManager.logoutUser();
        SceneManager.getInstance().showLoginScene();
    }

    /**
     * Redirects to the page for posting a new job.
     *
     * @param actionEvent The ActionEvent triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void postJob(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showPostJobScene();
    }

    /**
     * Redirects to the page displaying job applicants.
     *
     * @param actionEvent The ActionEvent triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void applicantsPage(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showApplicantsPage();
    }
}
