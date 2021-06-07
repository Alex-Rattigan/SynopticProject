import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.regex.Pattern;


public class SignUpController {

    //TextField variable definition
    @FXML
    private TextField forenameText, surnameText, usernameText, mobileNoText;

    //PasswordField variable definition
    @FXML
    private PasswordField passwordText;

    //Button variable definition
    @FXML
    private Button completeSignUpButton;

    //RadioButton variable definition
    @FXML
    private RadioButton intermediaryButton, fisherButton;

    //Creates the alert to be used
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    //SignUp method that is executed when user presses the submit button
    public void signUp() throws Exception {
        try {
            //variable definition from the TextFields
            String forename = forenameText.getText();
            String username = usernameText.getText();
            String surname = surnameText.getText();
            String password = passwordText.getText();
            String mobileNo = mobileNoText.getText();

            //Checks the RadioButtons to see which is selected
            if (intermediaryButton.isSelected()) {
                //Executes the checkDetails method to check that all details are valid
                if(checkDetails()) {
                    //DatabaseController method to insert a new Intermediary
                    DatabaseController.insertIntermediaryRecord(username, password, forename, surname, mobileNo);
                    //Sets the currentUser to the user that has just been created
                    MyFishingPal.currentUser = DatabaseController.checkIntermediaryExists(username);

                    //Changes to the next scene
                    Stage stage = null;
                    Parent nextScene = null;
                    stage = (Stage) completeSignUpButton.getScene().getWindow();
                    nextScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));
                    assert nextScene != null;
                    Scene scene = new Scene(nextScene);
                    stage.setScene(scene);
                    stage.setTitle("MyFishingPal");
                    stage.show();
                }
                //checks RadioButton to see if Fisher is selected
            } else if (fisherButton.isSelected())
            {
                //Executes the checkDetails method to check that all details are valid
                if(checkDetails()) {
                    //DatabaseController method to insert a new Fisher
                    DatabaseController.insertFisherRecord(username, password, forename, surname, mobileNo);
                    //Sets the currentUser to the user that has just been created
                    MyFishingPal.currentUser = DatabaseController.checkFisherExists(username);

                    //Changes to the next scene, got this code from Richard Blant's Software Engineering Coursework
                    Stage stage = null;
                    Parent nextScene = null;
                    stage = (Stage) completeSignUpButton.getScene().getWindow();
                    nextScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));
                    assert nextScene != null;
                    Scene scene = new Scene(nextScene);
                    stage.setScene(scene);
                    stage.setTitle("MyFishingPal");
                    stage.show();
                }
                // This else is if the user hasn't clicked either RadioButton
            } else{
                //Sets an Alert to the screen to describe what the error is
                alert.setTitle("Please check either Intermediary or Fisher");
                alert.setHeaderText("Please select an option");
                alert.showAndWait();
            }

            //This will catch the DatabaseController method to see if any of the information is invalid with the constraints
        }catch (Exception e){
            alert.setTitle("");
            alert.setHeaderText("Invalid information in atleast one field, or has been left blank");
            alert.showAndWait();
        }
    }

    //checkDetails method to return whethere or not the user can be created
    public boolean checkDetails(){
        //in the actual use we will have mobile numbers but to test it we used email addresses so any reference
        //to mobileNo is an email
        String mobileNo = mobileNoText.getText();

        //variable definitions
        String forename = forenameText.getText();
        String surname = surnameText.getText();
        String username = usernameText.getText();
        String password = passwordText.getText();

        //checks to see if any of the TextFields are empty, if they are show the alert
        if(forename.equals("") || surname.equals("") || username.equals("") || password.equals("") || mobileNo.equals("")){
            alert.setTitle("Fields empty");
            alert.setHeaderText("Atleast one of the fields are empty, please make sure they are all filled in");
            alert.showAndWait();
            return false;
        }

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

        //creates a LinkedList of Intermediary Users
        LinkedList<Intermediary> listOfIntermediaries = DatabaseController.selectAllIntermediaryRecords();
        //Goes through the whole list of Intermediaries
        for(Intermediary i : listOfIntermediaries) {
            //if the username is not unique, show this alert
            if(username.equalsIgnoreCase(i.getUsername())) {
                alert.setTitle("Invalid Username");
                alert.setHeaderText("The Username you have entered is already in use, please enter a new one");
                alert.showAndWait();
                return false;
                //if the mobileNo is not unique, show this alert
            } else if(mobileNo.equalsIgnoreCase(i.getMobileNo())){
                alert.setTitle("Invalid Mobile Number");
                alert.setHeaderText("The phone number you have entered is already in use, please enter a new one");
                alert.showAndWait();
                return false;
            }
        }

        //creates a LinkedList of Fisher Users
        LinkedList<Fisher> listOfFishers = DatabaseController.selectAllFisherRecords();
        //Goes through the whole list of Fishers
        for(Fisher f : listOfFishers) {
            //if the username is not unique, show this alert
            if (username.equalsIgnoreCase(f.getUsername())) {
                alert.setTitle("Invalid Username");
                alert.setHeaderText("The Username you have entered is already in use, please enter a new one");
                alert.showAndWait();
                return false;
                //if the mobileNo is not unique, show this alert
            } else if (mobileNo.equalsIgnoreCase(f.getMobileNo())) {
                alert.setTitle("Invalid Mobile Number");
                alert.setHeaderText("The phone number you have entered is already in use, please enter a new one");
                alert.showAndWait();
                return false;
            }
        }
        //if the information the user has input in all fields is valid and unique, returns true so the user can be created
        return true;
    }

}
