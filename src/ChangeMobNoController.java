/*******************************************************************************************************************
 * File: ChangeMobileNoController.java
 *
 * Date: 8/6/2021
 *
 * Author: Richey Blant
 *
 * Description: This is the class that adds functionality for the ChangeMobNo.fxml that makes it work, this page is
 * for changing the number for a user, on this page the current number is displayed and the user can enter a new
 * number.
 *
 ******************************************************************************************************************/

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

    //these are the text fields variable definitions
    @FXML
    private TextField currentNumber, newNumber;
    //these are the page button variable definitions
    @FXML
    private Button closeButton;

    //this creates the alert for the page
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    //this is what gets run when the page is loaded
    public void initialize(){
        displayNumber();
    }

    //this displays the current number for the user
    public void displayNumber(){
        //checks to see if the user is an intermediary
        if(MyFishingPal.currentUser instanceof Intermediary) {
            //gets the information for the current user
            Intermediary i = CSVController.selectIntermediaryRecord(((Intermediary) MyFishingPal.currentUser).getID());
            //sets the text field to the number of the current user
            currentNumber.setText(i.getMobileNo());
            //or checks to see if the user is a fisher
        } else if(MyFishingPal.currentUser instanceof Fisher){
            //gets the information for the current user
            Fisher f = CSVController.selectFisherRecord(((Fisher) MyFishingPal.currentUser).getID());
            //sets the text field to the number of the current user
            currentNumber.setText(f.getMobileNo());
        }
    }

    //this is the method that changes the users number when the confirm button is pressed
    public void changeNumber() throws IOException{
        //checks to see if the details are correct
        if(checkDetails()) {
            //checks to see if the user is an intermediary
            if (MyFishingPal.currentUser instanceof Intermediary) {
                //sets a variable for number
                String number = newNumber.getText();
                //updates the database cache with the new number
                CSVController.updateMobileNoIntermediary(((Intermediary) MyFishingPal.currentUser).getID(), number);
                //sets an alert to notify the user that the number has been changed successfully
                alert.setTitle("Successfully Changed Phone Number");
                alert.setHeaderText("The phone number has successfully been changed");
                alert.showAndWait();
                //checks to see if the user is a fisher
            } else if (MyFishingPal.currentUser instanceof Fisher) {
                //sets a variable for number
                String number = newNumber.getText();
                //updates the database cache with the new number
                CSVController.updateMobileNoFisher(((Fisher) MyFishingPal.currentUser).getID(), number);
                //sets an alert to notify the user that the number has been changed successfully
                alert.setTitle("Successfully Changed Phone Number");
                alert.setHeaderText("The phone number has successfully been changed");
                alert.showAndWait();
            }
        }
    }


    //this is the method that checks the users details
    public boolean checkDetails() throws IOException{
        String mobileNo = newNumber.getText();
        //This is the regex for seeing if an email address is valid
        String mobileRegex = "[A-Za-z0-9._-]+@+[A-Za-z0-9]+[.]+[A-Za-z0-9]+[.]?[A-Za-z0-9]+";
        Pattern pattern = Pattern.compile(mobileRegex);

        //If the pattern does not match what the user has input, then show the alert
        if (!pattern.matcher(mobileNo).matches()) {
            alert.setTitle("Phone Number invalid");
            alert.setHeaderText("Please enter a valid phone number (email address)");
            alert.showAndWait();
            return false;
        }
        //this creates a list of all the intermediaries
        LinkedList<Intermediary> listOfIntermediaries = CSVController.selectAllIntermediaryRecords();
        //for loop to go through all intermediaries in the list
        for(Intermediary i : listOfIntermediaries) {
            //if the mobile number is equal to a number in the list
            if(mobileNo.equalsIgnoreCase(i.getMobileNo())){
                //show an alert to notify the user that the number is already in use
                alert.setTitle("Invalid Mobile Number");
                alert.setHeaderText("The phone number you have entered is already in use, please enter a new one");
                alert.showAndWait();
                return false;
            }
        }
        //this creates a list of all the intermediaries
        LinkedList<Fisher> listOfFishers = CSVController.selectAllFisherRecords();
        //for loop to go through all intermediaries in the list
        for(Fisher f : listOfFishers) {
            //if the mobile number is equal to a number in the list
            if (mobileNo.equalsIgnoreCase(f.getMobileNo())) {
                //show an alert to notify the user that the number is already in use
                alert.setTitle("Invalid Mobile Number");
                alert.setHeaderText("The phone number you have entered is already in use, please enter a new one");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }

    //this is the method that goes back to the previous page if the user clicks the close button
    public void close() throws IOException
    {
        //changes the scene back to the profile
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) closeButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }
}
