package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.Posts.JobPosting;
import com.example.csc325.csc325.SceneManager;
import com.example.csc325.csc325.UserSessionManager;
import com.example.csc325.csc325.users.Employer;
import com.example.csc325.csc325.users.User;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class JobPostingController {
    public TextField jobTitle;
    public TextField jobLocation;
    public TextField salary;
    public TextField keywords;
    public TextArea description;

    public void onCancel(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showEmployerProfileScene();
    }

    public void onPost(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        User user = UserSessionManager.getUser();

        String jobTitle = this.jobTitle.getText();
        String jobLocation = this.jobLocation.getText();
        String salary = this.salary.getText();
        String[] keywords = this.keywords.getText().split("\\s+") ;
        String description = this.description.getText();
        String company = ((Employer) user).getCompanyName();
        String companyId = user.getId();
        JobPosting job = new JobPosting(jobTitle, company,salary,description, new ArrayList<>(Arrays.asList(keywords)), companyId, jobLocation);
        job.save();
        SceneManager.getInstance().showMainScene();
    }
}
