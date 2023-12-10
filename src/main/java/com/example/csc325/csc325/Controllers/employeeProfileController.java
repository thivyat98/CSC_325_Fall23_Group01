package com.example.csc325.csc325.Controllers;


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
        username.setText(user.getEmail());
    }

}
