package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Controller class for handling the logic of the successful registration view.
 */
public class SuccessfulRegistrationController {
    @FXML
    public Label lblSuccessMsg;

    @FXML
    public Button backToLoginBtn;

    /**
     * Navigates back to the login scene.
     *
     * @throws IOException          If an I/O error occurs.
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
    public void backToLogin() throws IOException, ExecutionException, InterruptedException {
        SceneManager.getInstance().showLoginScene();
    }
}
