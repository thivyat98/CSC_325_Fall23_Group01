package com.example.csc325.csc325.Controllers;


import com.example.csc325.csc325.users.Employee;
import com.example.csc325.csc325.users.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;

public class employeeProfileController {
    @FXML
    public Label username;
    @FXML
    private ListView<String> skillListView;

    @FXML
    private TextField skill;

    @FXML
    private Button addSkill;

    @FXML
    private ImageView profilePicture;

    @FXML
    public TextField FirstName;

    @FXML
    private TextField LastName;

    @FXML
    private TextField Email;

    @FXML
    private TextField phoneNumber;

    private User user;

    @FXML
    void addSkilltoList(MouseEvent event) {
        skillListView.getItems().add(skill.getText());
    }

    @FXML
    void removeSkillfromList(MouseEvent event) {
        int selectedID = skillListView.getSelectionModel().getSelectedIndex();
        skillListView.getItems().remove(selectedID);
    }

    @FXML
    protected void showProfilePicture() {
        File file = (new FileChooser()).showOpenDialog(profilePicture.getScene().getWindow());
        if (file != null) {
            profilePicture.setImage(new Image(file.toURI().toString()));
        }
    }

    public void setUser(User user) {
        this.user = user;
        loadUserData();
    }

    private void loadUserData() {
        if (user instanceof Employee) {
            Employee employee = (Employee) user;
            username.setText(employee.getEmail());
            FirstName.setText(employee.getFirstName());
            LastName.setText(employee.getLastName());
            Email.setText(employee.getEmail());
            phoneNumber.setText(employee.getPhone());
            loadSkills(employee);
            loadProfilePicture(employee);
        }
    }

    private void loadSkills(Employee employee) {
        skillListView.getItems().addAll(employee.getSkills());
    }

    private void loadProfilePicture(Employee employee) {
        // Assuming Employee class has a method like getProfilePicturePath()
        String profilePicturePath = "";  // Add the corresponding method in the Employee class
        if (profilePicturePath != null && !profilePicturePath.isEmpty()) {
            profilePicture.setImage(new Image(new File(profilePicturePath).toURI().toString()));
        }
    }

    // This method is called when the scene is loaded, it should auto-populate the fields with the user data from UserSessionManager
    public void onLoad() {
        if (user != null) {
            loadUserData();
        }
    }
}
