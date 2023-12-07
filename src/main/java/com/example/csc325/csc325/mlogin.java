package com.example.csc325.csc325;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class mlogin {

    @FXML
    public Button btn_signUp;

    @FXML
    public PasswordField hiddenPasswordField;

    @FXML
    public TextField visiblePasswordField;
    @FXML
    public TextField passwordLabel;

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

    public void change(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showSignUpScene();
    }

}
