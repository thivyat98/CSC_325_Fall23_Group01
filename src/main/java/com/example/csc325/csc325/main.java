package com.example.csc325.csc325;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/**
 * The main class responsible for initializing the JavaFX application and managing the Firebase setup.
 */
public class main extends Application {
    /**
     * The main method to launch the JavaFX application.
     *
     * @param args Command-line arguments (not used).
     * @throws IOException          If an I/O error occurs.
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        // Load the Firebase service account credentials
        InputStream serviceAccount = new FileInputStream("src/main/resources/com/example/csc325/csc325/privatekey.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

        // Initialize Firebase with the loaded credentials
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);

        // Launch the JavaFX application
        launch();
    }

    /**
     * Initializes the JavaFX application, sets up the primary stage, and shows the login scene.
     *
     * @param stage The primary stage of the JavaFX application.
     * @throws IOException If an I/O error occurs.
     */
    //thivya 
    @Override
    public void start(Stage stage) throws IOException {
        // Set the primary stage for the SceneManager
        SceneManager.getInstance().setStage(stage);

        // Show the login scene
        SceneManager.getInstance().showLoginScene();
    }
}
