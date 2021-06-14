/*******************************************************************************************************************
 * File: HelpController.java
 *
 * Date: 8/6/2021
 *
 * Author: Sophie Musk
 *
 * Description: This is the controller that adds functionality to the Help.fxml page, this page provides
 * pointers and information for the user if they get stuck using the app and need help.
 *
 ******************************************************************************************************************/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelpController {

    // These are the variable definitions for the navigation buttons
    @FXML
    private Button helpButton, infoButton, jobListingButton, viewJobsButton, profileButton;

    // This method takes the user to the jobs view page
    public void goToJobView() throws IOException{
        // checks to see if the user is an intermediary or fisher
        if(MyFishingPal.currentUser instanceof Intermediary){
            // take the user to the intermediary page if intermediary
            Stage stage = null;
            Parent nextScene = null;
            stage = (Stage) viewJobsButton.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("IntermediaryView.fxml"));
            assert nextScene != null;
            Scene scene = new Scene(nextScene);
            stage.setScene(scene);
            stage.setTitle("MyFishingPal");
            stage.show();
        } else if(MyFishingPal.currentUser instanceof Fisher){
            // take the user to the fisher page if fisher
            Stage stage = null;
            Parent nextScene = null;
            stage = (Stage) viewJobsButton.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("FisherView.fxml"));
            assert nextScene != null;
            Scene scene = new Scene(nextScene);
            stage.setScene(scene);
            stage.setTitle("MyFishingPal");
            stage.show();
        }
    }

    // takes the user to the jobs listing page
    public void goToJobListing() throws IOException{
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) jobListingButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobListings.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    // takes the user to their profile page
    public void goToProfile() throws IOException{
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) profileButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    // takes the user to the fishing info page
    public void goToFishingInfo() throws IOException{
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) infoButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("FishingInfo.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    //this is the method that changes the scenes for the navigation bar, this is code from Richey Blant's Software
    //Engineering Coursework
    public void changeScene(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent nextScene = null;

        //this checks which button has been pressed and goes to the appropriate page
        if(event.getSource() == helpButton) {
            stage = (Stage) helpButton.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("Help.fxml"));
        } else if(event.getSource() == infoButton){
            stage = (Stage) infoButton.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("FishingInfo.fxml"));
        } else if(event.getSource() == jobListingButton){
            stage = (Stage) jobListingButton.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("JobListings.fxml"));
            //this one has to check which type of user is using the system before it can go to the correct page
        } else if(event.getSource() == viewJobsButton){
            stage = (Stage) viewJobsButton.getScene().getWindow();
            //if the user is a fisher
            if(MyFishingPal.currentUser instanceof Fisher)
            {
                //go to the fisher view
                nextScene = FXMLLoader.load(getClass().getResource("FisherView.fxml"));
            }
            //if the user is an intermediary
            else
            {
                nextScene = FXMLLoader.load(getClass().getResource("IntermediaryView.fxml"));
            }
        } else if(event.getSource() == profileButton){
            stage = (Stage) profileButton.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        }
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.show();
    }


}
