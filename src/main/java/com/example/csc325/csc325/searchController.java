package com.example.csc325.csc325;

import com.example.csc325.csc325.Posts.JobPosting;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class searchController {
    @FXML
    public TextField searchField;

    // Replace the local list with a Firestore collection reference
    private CollectionReference jobCollection;


    public searchController() {
        // Assuming 'jobs' is the name of your Firestore collection
        this.jobCollection = FirestoreClient.getFirestore().collection("jobs");
    }

    //ToDo: Implement search functionality for jobs with a set of keywords
    public void searchJobs(ActionEvent actionEvent) {
        String searchTerm = searchField.getText().toLowerCase();

        try {
            // Query Firestore for jobs
            ApiFuture<QuerySnapshot> future = jobCollection.whereArrayContains("keywords", searchTerm).get();
            QuerySnapshot querySnapshot = future.get();

            // Display the matching jobs
            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                JobPosting job = document.toObject(JobPosting.class);
                if (job != null) {
                    System.out.println("Job Matches: " + job.getJobTitle());
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
