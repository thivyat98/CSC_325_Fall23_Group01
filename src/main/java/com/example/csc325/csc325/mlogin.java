package com.example.csc325.csc325;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class mlogin {

    @FXML
    public Button loginBtn;

    @FXML
    public TextField usernameLabel;

    @FXML
    public PasswordField hiddenPasswordField;

    @FXML
    public TextField visiblePasswordField;
    @FXML
    public TextField passwordLabel;

    @FXML
    public Label lblErrorMsg;

    public void togglePasswordVisibility(ActionEvent actionEvent) {
        if (visiblePasswordField.isVisible()) {
            hiddenPasswordField.setText(visiblePasswordField.getText());
            hiddenPasswordField.setVisible(true);
            passwordLabel.setText(getDots(hiddenPasswordField.getText()));
            passwordLabel.setVisible(true);
            visiblePasswordField.setVisible(false);
        } else {
            visiblePasswordField.setText(hiddenPasswordField.getText());
            visiblePasswordField.setVisible(true);
            visiblePasswordField.requestFocus();
            passwordLabel.setVisible(false);
            hiddenPasswordField.setVisible(false);
        }
    }

    private String getDots(String text) {
        return "•".repeat(text.length());
    }

    public void signup(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showSignUpScene();
    }

    public void signin(ActionEvent actionEvent) throws IOException {

        SceneManager.getInstance().showMainScene();
    }


}
