package com.example.csc325.csc325;

import com.example.csc325.csc325.users.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static SceneManager instance;
    private Stage stage;

    private SceneManager() {}

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showProfileScene(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("employeeProfile.fxml"));
        Parent root = loader.load();
        employeeProfileController profileController = loader.getController();
        profileController.setUser(user);
        stage.setScene(new Scene(root));
        stage.setTitle("User Profile");
    }

    public void showSignUpScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("msignup.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Signup");
    }

    public void showLoginScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginView.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("login");
    }
}
