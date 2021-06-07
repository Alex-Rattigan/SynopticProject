import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeMobNoController {


    @FXML
    private TextField currentNumber, newNumber;
    @FXML
    private Button confirmButton;
    @FXML
    private Button helpButton, infoButton, jobListingButton, viewJobsButton, profileButton;

    public void initialize(){
        displayNumber();
    }

    public void displayNumber(){
        //if Object instanceOf Intermediary
            //Intermediary i = DatabaseController.selectIntermediaryRecord(currentUserId);
            //currentNumber.setText(i.getMobileNo)
        //else if Object instanceOf Fisher
            //Fisher f = DatabaseController.selectFisherRecord(currentUserId);
            //currentNumber.setText(f.getMobileNo)
    }

    public void changeNumber(){
        //if Object instanceOf Intermediary
            //String newNumber = newNumber.getText();
            //DatabaseController.updateMobileNoIntermediary(currentUserId, newNumber);
        //else if Object instanceOf Fisher
            //String number = newNumber.getText();
            //DatabaseController.updateMobileNoFisher(currentUserId, newNumber);

    }


    public void changeScene(ActionEvent event) throws IOException {
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
