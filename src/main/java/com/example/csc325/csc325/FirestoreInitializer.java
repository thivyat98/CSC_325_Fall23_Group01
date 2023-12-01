package com.example.csc325.csc325;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.concurrent.ExecutionException;

public class FirestoreInitializer {
    public static Firestore firebase() throws IOException, ExecutionException, InterruptedException {

        return FirestoreClient.getFirestore();


}
}
