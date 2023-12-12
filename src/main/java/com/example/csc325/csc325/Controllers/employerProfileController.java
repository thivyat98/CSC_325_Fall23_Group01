package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.SceneManager;
import com.example.csc325.csc325.UserSessionManager;
import com.example.csc325.csc325.users.Employee;
import com.example.csc325.csc325.users.Employer;
import com.example.csc325.csc325.users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

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
    public void onLoad() {
        User user = UserSessionManager.getUser();
        if (user instanceof Employer employer) {
            // Set the username label to the employee's email
            username.setText(employer.getEmail());
            // Set the employeeName label to the employee's full name
            companyName.setText(employer.getCompanyName());
            phoneNumber.setText(employer.getPhone());
            about.setText(user.getAbout());
        }
    }

    public void saveProfile(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {

        User user = UserSessionManager.getUser();
        user.setAbout(about.getText());
        user.save();

        SceneManager.getInstance().showMainScene();
        //Want to make a dshboard where we see how many have applied?
    }

    public void logOutHandler(ActionEvent actionEvent) throws IOException {
        UserSessionManager.logoutUser();
        SceneManager.getInstance().showLoginScene();
    }

    public void postJob(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showPostJobScene();
    }

    public void applicantsPage(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showApplicantsPage();
    }


}
