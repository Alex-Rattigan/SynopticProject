/*******************************************************************************************************************
 * File: ChangePasswordController.java
 *
 * Date: 8/6/2021
 *
 * Author: Richey Blant
 *
 * Description: This is the class that adds functionality to the ChangePassword.fxml page, this is the page that
 * allows a user to change their password. The user will have to enter their current password, and a new password, if
 * the current password is correct and the new passwords are the same then the password will be updated.
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

public class ChangePasswordController {

    //these are the variable definitions for the textfields
    @FXML
    private TextField currentPassword, newPassword0, newPassword1;
    //these are the variable definitions for the page buttons
    @FXML
    private Button closeButton;

    //this creates the alert for the page
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    //this is the method to change the password for a user, this gets called when the user presses the confirm button
    public void changePassword() throws IOException{
        //checks if the current user is a intermediary
        if(MyFishingPal.currentUser instanceof Intermediary) {
            //gets the current users information
            Intermediary i = CSVController.selectIntermediaryRecord(((Intermediary) MyFishingPal.currentUser).getID());
            //variable definitions for the passwords, to check them against eachother
            String currentPassword0 = i.getPassword();
            String currentPassword1 = currentPassword.getText();
            String newPass0 = newPassword0.getText();
            String newPass1 = newPassword1.getText();
            //checks to see if the current passwords are equal, and the new passwords are equal
            if (currentPassword0.equals(currentPassword1) && newPass0.equals(newPass1)){
                //updates the database cache with the new password
                CSVController.updatePasswordIntermediary(((Intermediary) MyFishingPal.currentUser).getID(), newPass0);
                //sets an alert to say the password has been changed
                alert.setTitle("Successfully Changed Password");
                alert.setHeaderText("The password has successfully been changed");
                alert.showAndWait();
            }else {
                //sets an alert to say the password could not be changed
                alert.setTitle("Incorrect passwords");
                alert.setHeaderText("Either the current password did not match our records, or the new passwords weren't the same");
                alert.showAndWait();
            }
            //if the user is a fisher
        } else if(MyFishingPal.currentUser instanceof Fisher){
            //gets the current users information
            Fisher f = CSVController.selectFisherRecord(((Fisher) MyFishingPal.currentUser).getID());
            //variable definitions for the passwords, to check them against eachother
            String currentPassword0 = f.getPassword();
            String currentPassword1 = currentPassword.getText();
            String newPass0 = newPassword0.getText();
            String newPass1 = newPassword1.getText();
            //checks to see if the current passwords are equal, and the new passwords are equal
            if (currentPassword0.equals(currentPassword1) && newPass0.equals(newPass1)){
                //updates the database cache with the new password
                CSVController.updatePasswordFisher(((Fisher) MyFishingPal.currentUser).getID(), newPass0);
                //sets an alert to say the password has been changed
                alert.setTitle("Successfully Changed Password");
                alert.setHeaderText("The password has successfully been changed");
                alert.showAndWait();
            }else {
                //sets an alert to say the password could not be changed
                alert.setTitle("Incorrect passwords");
                alert.setHeaderText("Either the current password did not match our records, or the new passwords weren't the same");
                alert.showAndWait();
            }
        }
    }

    //this is the method that goes back to the previous page if the user clicks the close button
    public void close() throws IOException
    {
        //changes the scence back to the intermediary view
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
