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

    @FXML
    private TextField forenameTxt, surnameTxt, usernameTxt, passwordTxt, mobileNoTxt;

    @FXML
    private Button completeSignUpButton;

    @FXML
    private RadioButton intermediaryButton, fisherButton;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void signUp() throws Exception {
        try {
            String forename = forenameTxt.getText();
            String username = usernameTxt.getText();
            String surname = surnameTxt.getText();
            String password = passwordTxt.getText();
            String mobileNo = mobileNoTxt.getText();

            if (intermediaryButton.isSelected()) {
                if(checkDetails()) {
                    DatabaseController.insertIntermediaryRecord(forename, surname, username, password, mobileNo);
                    MyFishingPal.currentUser = DatabaseController.checkIntermediaryExists(username);
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
            } else if (fisherButton.isSelected())
            {
                if(checkDetails()) {
                    DatabaseController.insertFisherRecord(username, password, forename, surname, mobileNo);
                    MyFishingPal.currentUser = DatabaseController.checkFisherExists(username);
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
            } else{
                alert.setTitle("Please check either Intermediary or Fisher");
                alert.setHeaderText("Please select an option");
                alert.showAndWait();
            }

        }catch (Exception e){
            alert.setTitle("");
            alert.setHeaderText("Invalid information in atleast one field, or has been left blank");
            alert.showAndWait();
        }
    }

    public boolean checkDetails(){
        //mobile numbers are emails
        String mobileNo = mobileNoTxt.getText();

        String forename = forenameTxt.getText();
        String surname = surnameTxt.getText();
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        if(forename.equals("") || surname.equals("") || username.equals("") || password.equals("") || mobileNo.equals("")){
            alert.setTitle("Fields empty");
            alert.setHeaderText("Atleast one of the fields are empty, please make sure they are all filled in");
            alert.showAndWait();
            return false;
        }

        //Make these variables at the top of the method
        //this comes from Alex Rattigan's Software Engineering Code
        //mobile numbers are emails in our system
        String mobileRegex = "[A-Za-z0-9._-]+@+[A-Za-z0-9]+[.]+[A-Za-z0-9]+[.]?[A-Za-z0-9]+";
        Pattern pattern = Pattern.compile(mobileRegex);

        //Use this to test the email
        if (!pattern.matcher(mobileNo).matches()) {
            //Email is invalid
            alert.setTitle("Phone Number invalid");
            alert.setHeaderText("Please enter a valid phone number (email address)");
            alert.showAndWait();
            return false;
        }

        LinkedList<Intermediary> listOfIntermediaries = DatabaseController.selectAllIntermediaryRecords();
        for(Intermediary i : listOfIntermediaries) {
            if(username.equalsIgnoreCase(i.getUsername())) {
                alert.setTitle("Invalid Username");
                alert.setHeaderText("The Username you have entered is already in use, please enter a new one");
                alert.showAndWait();
                return false;
            } else if(mobileNo.equalsIgnoreCase(i.getMobileNo())){
                alert.setTitle("Invalid Mobile Number");
                alert.setHeaderText("The phone number you have entered is already in use, please enter a new one");
                alert.showAndWait();
                return false;
            }
        }

        LinkedList<Fisher> listOfFishers = DatabaseController.selectAllFisherRecords();
        for(Fisher f : listOfFishers) {
            if (username.equalsIgnoreCase(f.getUsername())) {
                alert.setTitle("Invalid Username");
                alert.setHeaderText("The Username you have entered is already in use, please enter a new one");
                alert.showAndWait();
                return false;
            } else if (mobileNo.equalsIgnoreCase(f.getMobileNo())) {
                alert.setTitle("Invalid Mobile Number");
                alert.setHeaderText("The phone number you have entered is already in use, please enter a new one");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }

}
