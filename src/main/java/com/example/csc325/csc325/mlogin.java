package com.example.csc325.csc325;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class mlogin {
    public Button btn_signUp;



    public void togglePasswordVisibility(ActionEvent actionEvent) {
    }

    public void signup(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showSignUpScene();
    }
}
