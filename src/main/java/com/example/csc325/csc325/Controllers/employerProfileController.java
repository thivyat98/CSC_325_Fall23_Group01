package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.SceneManager;
import com.example.csc325.csc325.UserSessionManager;
import com.example.csc325.csc325.users.Employee;
import com.example.csc325.csc325.users.Employer;
import com.example.csc325.csc325.users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class employerProfileController {
    @FXML
    public Label username;
    @FXML
    public Label companyName;

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
        }
    }

    public void saveProfile(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        SceneManager.getInstance().showMainScene();
        //Want to make a dshboard where we see how many have applied?
    }
}
