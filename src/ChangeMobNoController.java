import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class ChangeMobNoController {


    @FXML
    private TextField currentNumber, newNumber;
    @FXML
    private Button confirmButton;
    @FXML
    private Button helpButton, infoButton, jobListingButton, viewJobsButton, profileButton;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void initialize(){
        displayNumber();
    }

    public void displayNumber(){
        if(MyFishingPal.currentUser instanceof Intermediary) {
            Intermediary i = DatabaseController.selectIntermediaryRecord(((Intermediary) MyFishingPal.currentUser).getID());
            currentNumber.setText(i.getMobileNo());
        } else if(MyFishingPal.currentUser instanceof Fisher){
            Fisher f = DatabaseController.selectFisherRecord(((Fisher) MyFishingPal.currentUser).getID());
            currentNumber.setText(f.getMobileNo());
        }
    }

    public void changeNumber() throws IOException{
        if(checkDetails()) {
            if (MyFishingPal.currentUser instanceof Intermediary) {
                String number = newNumber.getText();
                DatabaseController.updateMobileNoIntermediary(((Intermediary) MyFishingPal.currentUser).getID(), number);
                Stage stage = null;
                Parent nextScene = null;
                stage = (Stage) confirmButton.getScene().getWindow();
                nextScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));
                assert nextScene != null;
                Scene scene = new Scene(nextScene);
                stage.setScene(scene);
                stage.setTitle("MyFishingPal");
                stage.show();
            } else if (MyFishingPal.currentUser instanceof Fisher) {
                String number = newNumber.getText();
                DatabaseController.updateMobileNoFisher(((Fisher) MyFishingPal.currentUser).getID(), number);
                Stage stage = null;
                Parent nextScene = null;
                stage = (Stage) confirmButton.getScene().getWindow();
                nextScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));
                assert nextScene != null;
                Scene scene = new Scene(nextScene);
                stage.setScene(scene);
                stage.setTitle("MyFishingPal");
                stage.show();
            }
        }
    }


    public boolean checkDetails() throws IOException{
        String mobileNo = newNumber.getText();
        //This is the regex for seeing if an email address is valid, this code comes from Alex Rattigan's Software
        //Engineering Coursework
        String mobileRegex = "[A-Za-z0-9._-]+@+[A-Za-z0-9]+[.]+[A-Za-z0-9]+[.]?[A-Za-z0-9]+";
        Pattern pattern = Pattern.compile(mobileRegex);

        //If the pattern does not match what the user has input, then show the alert
        if (!pattern.matcher(mobileNo).matches()) {
            alert.setTitle("Phone Number invalid");
            alert.setHeaderText("Please enter a valid phone number (email address)");
            alert.showAndWait();
            return false;
        }
        LinkedList<Intermediary> listOfIntermediaries = DatabaseController.selectAllIntermediaryRecords();
        for(Intermediary i : listOfIntermediaries) {
            if(mobileNo.equalsIgnoreCase(i.getMobileNo())){
                alert.setTitle("Invalid Mobile Number");
                alert.setHeaderText("The phone number you have entered is already in use, please enter a new one");
                alert.showAndWait();
                return false;
            }
        }
        LinkedList<Fisher> listOfFishers = DatabaseController.selectAllFisherRecords();
        for(Fisher f : listOfFishers) {
            if (mobileNo.equalsIgnoreCase(f.getMobileNo())) {
                alert.setTitle("Invalid Mobile Number");
                alert.setHeaderText("The phone number you have entered is already in use, please enter a new one");
                alert.showAndWait();
                return false;
            }
        }
        return true;
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

            if(MyFishingPal.currentUser instanceof Fisher)
            {
                nextScene = FXMLLoader.load(getClass().getResource("FisherView.fxml"));
            }
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
