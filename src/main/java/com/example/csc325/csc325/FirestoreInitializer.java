package com.example.csc325.csc325;


import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

//DONT USE
public class FirestoreInitializer {
    public static Firestore firebase() throws IOException, ExecutionException, InterruptedException {

        return FirestoreClient.getFirestore();


    }
}
