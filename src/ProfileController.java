/*******************************************************************************************************************
 * File: ProfileController.java
 *
 * Date: 7/6/2021
 *
 * Author: Richey Blant
 *
 * Description: This is the profile controller, this is the page that makes the functionality for the Profile.fxml
 * on this page, the user can see their information and have the options to change password, change mobile number
 * or log out if they are finished using the system.
 *
 ******************************************************************************************************************/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {

    //textfield variable definitions
    @FXML
    private TextField userForename, userSurname, userNumber, userUsername;
    ///page button variable definitions
    @FXML
    private Button changePasswordButton, changeNumberButton, logoutButton;
    //navigation bar button variable definitions
    @FXML
    private Button helpButton, infoButton, jobListingButton, viewJobsButton, profileButton;

    //this is what gets run when the page gets launched
    public void initialize(){
        displayInfo();
    }

    //this is the method that gets the information that gets displayed on the profile
    public void displayInfo(){
        //if the currentuser is an intermediary, run this code
        if(MyFishingPal.currentUser instanceof Intermediary) {
            //this gets the intermediaries information from the database cache
            Intermediary i = CSVController.selectIntermediaryRecord(((Intermediary) MyFishingPal.currentUser).getID());
            //this sets the textfields to the information from the intermediary
            userUsername.setText(i.getUsername());
            userForename.setText(i.getFname());
            userSurname.setText(i.getSname());
            userNumber.setText(i.getMobileNo());
            //if the currentuser is a fisher, run this code
        } else if(MyFishingPal.currentUser instanceof Fisher){
            //this gets the fisher information from the database cache
            Fisher f = CSVController.selectFisherRecord(((Fisher) MyFishingPal.currentUser).getID());
            //this sets the textfields to the information from the fisher
            userUsername.setText(f.getUsername());
            userForename.setText(f.getFname());
            userSurname.setText(f.getSname());
            userNumber.setText(f.getMobileNo());
        }
    }

    //this is the method that goes to the page to change the users password once the button is pressed
    public void changePassword() throws IOException{
        //changes the scene to the change password page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) changePasswordButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    //this is the method that goes to the page to change the users mobile number once the button is pressed
    public void changeNumber() throws IOException{
        //changes the scene to the change mobile number page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) changeNumberButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("ChangeMobNo.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    //this is the method that goes logs the current user out when it is pressed
    public void logOut() throws IOException{
        //this sets the current user to null
        MyFishingPal.currentUser = null;
        //changes the scene to the login page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) logoutButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("Login.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    //this is the method that changes the scenes for the navigation bar
    public void changeScene(ActionEvent event) throws IOException{
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
                //go to the intermediary view
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
