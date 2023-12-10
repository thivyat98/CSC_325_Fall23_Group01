package com.example.csc325.csc325;

import com.example.csc325.csc325.Controllers.employeeProfileController;
import com.example.csc325.csc325.Controllers.searchController;
import com.example.csc325.csc325.users.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class SceneManager {
    private static SceneManager instance;
    private Stage stage;

    private SceneManager() {
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setMaximized(true);
        this.stage.isAlwaysOnTop();
        this.stage.setResizable(false);


    }

    public void showProfileScene(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("employeeProfile.fxml"));
        Parent root = loader.load();
        employeeProfileController profileController = loader.getController();
        profileController.onLoad();
        stage.setScene(new Scene(root));
        stage.setTitle("User Profile");
    }

    public void showSignUpScene() throws IOException {
        stage.hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("msignup.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Signup");
        stage.show();
    }

    public void showBussinessSignUpScene() throws IOException {
        stage.hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("employerSignup.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Signup");
        stage.show();
    }

    public void showLoginScene() throws IOException {
        stage.hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mlogin.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.show();
    }

    public void showMainScene() throws IOException, ExecutionException, InterruptedException {
        stage.hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchPage.fxml"));
        Parent root = loader.load();
        searchController profileController = loader.getController();
        profileController.onLoad();
        this.stage.setMinWidth(600);
        this.stage.setMinHeight(800);
        stage.setScene(new Scene(root));
        stage.setTitle("Job Finder");
        stage.show();
    }

    public void showSuccessfulRegScene() throws IOException {
        stage.hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("successfulRegistration.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Success");
        stage.show();
    }

    public void showEmployeeProfileScene() throws IOException {
        stage.hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("employeeProfile.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Employer Profile");
        stage.show();
    }
}
