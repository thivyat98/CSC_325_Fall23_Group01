package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.SceneManager;
import com.example.csc325.csc325.UserSessionManager;
import com.example.csc325.csc325.users.Employee;
import com.example.csc325.csc325.users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class employeeProfileController {
    @FXML
    public Label username;
    @FXML
    public Label employeeName;
    public TextArea about;
    @FXML
    private ListView<String> skillListView;
    @FXML
    private TextField skill;
    @FXML
    private Button addSkill;
    @FXML
    private ImageView profilePicture;
    @FXML
    private TextField Email;
    @FXML
    private Label phoneNumber;

    @FXML
    private Button saveButton;

    @FXML
    private Button logOutBtn;

    @FXML
    void addSkilltoList(ActionEvent event) throws ExecutionException, InterruptedException {
        User user = UserSessionManager.getUser();
        if(skill.getText().isEmpty()){
            return;
        }
        skillListView.getItems().add(skill.getText());
        ((Employee) user).addSkill(skill.getText());
        skill.clear();
    }

    @FXML
    void removeSkillfromList(ActionEvent event) throws ExecutionException, InterruptedException {
        User user = UserSessionManager.getUser();
        int selectedID = skillListView.getSelectionModel().getSelectedIndex();
        String selectString = skillListView.getSelectionModel().getSelectedItem();
        skillListView.getItems().remove(selectedID);
        ((Employee) user).removeSkill(selectString);
    }


    private void loadSkills(Employee employee) {
        skillListView.getItems().addAll(employee.getSkills());
    }



    // This method is called when the scene is loaded, it should auto-populate the fields with the user data from UserSessionManager
    public void onLoad() {
        User user = UserSessionManager.getUser();
        if (user instanceof Employee employee) {
            // Set the username label to the employee's email
            username.setText(employee.getEmail());
            // Set the employeeName label to the employee's full name
            employeeName.setText(employee.getFirstName() + " " + employee.getLastName());
            phoneNumber.setText(employee.getPhone());
            about.setText(user.getAbout());
            loadSkills(employee);
        }
    }

    public void saveProfile(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        User user = UserSessionManager.getUser();
        user.setAbout(about.getText());
        user.save();

        SceneManager.getInstance().showMainScene();
    }

    public void logOutHandler(ActionEvent actionEvent) throws IOException {
        UserSessionManager.logoutUser();
        SceneManager.getInstance().showLoginScene();
    }
}
