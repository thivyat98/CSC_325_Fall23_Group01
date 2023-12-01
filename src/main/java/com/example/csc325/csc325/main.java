package com.example.csc325.csc325;

import com.example.csc325.csc325.users.Employee;
import com.example.csc325.csc325.users.User;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.getInstance().setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("mlogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        InputStream serviceAccount = new FileInputStream("src/main/java/com/example/csc325/csc325/privatekey.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
//        Employee user = new Employee("test", "test", "test");
//        ArrayList<String> skills = new ArrayList<>();
//        skills.add("Java");
//        skills.add("C++");
//        user.setSkills(skills);
//        user.save();
        launch();
    }
}