import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {

    @FXML
    private TextField userForename, userSurname, userNumber;
    @FXML
    private Button changePasswordButton, changeNumberButton, logoutButton;
    @FXML
    private Button helpButton, infoButton, jobListingButton, viewJobsButton, profileButton;

    public void displayInfo(){
        //if current user is present in intermediary
            //do this
        //else if current user is present in fisher
    }

    public void changePassword(){
        //if current user is present in intermediary
            //do intermediarychangepass
        //else if current user is present in fisher
            //do fisherchangepass
    }

    public void changeNumber(){
        //if current user is present in intermediary
            //do intermediarychangenumber
        //else if current user is present in fisher
            //do fisherchangenumber
    }





    public void changeScene(ActionEvent event) throws IOException{
        Stage stage = null;
        Parent nextScene = null;

        if(event.getSource() == helpButton) {
            stage = (Stage) helpButton.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("Help.fxml"));
        } else if(event.getSource() == infoButton){
            stage = (Stage) infoButton.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("FishingInfo.fxml"));
        } else if(event.getSource() == jobListingButton){
            stage = (Stage) jobListingButton.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("JobListings.fxml"));
        } else if(event.getSource() == viewJobsButton){
            stage = (Stage) viewJobsButton.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("JobDetails.fxml"));
        } else if(event.getSource() == profileButton){
            stage = (Stage) profileButton.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        }
    }


}
