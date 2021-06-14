/****************************************************************************************************************************************************************
 * File: LoginController
 *
 * Date: 7th June
 *
 * Author: Thomas Myers, Richey blant
 *
 * Reference:
 *
 * Description: A login page for both the intermediary and the fishermen
 *
 * History: 7/6/2021 - v1 Thomas Created Initial prototype
 *          8/6/2021 - v1.1 Richey added alerts rather than changing labels for errors
 *
*****************************************************************************************************************************************************************/

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.IOException;
import javafx.stage.Stage;


public class LoginController
{
    @FXML
    private PasswordField passwordtxt;

    @FXML
    private TextField usernametxt;

    @FXML
    private Button loginbtn, registerButton;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    //changes the scene to the register screen
    public void register() throws IOException{
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) registerButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void login() throws IOException
    {
        //if the user is logged in, change scene
        if(checkPassword()){
            Stage stage = null;
            Parent nextScene = null;
            stage = (Stage) loginbtn.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("FishingInfo.fxml"));
            assert nextScene != null;
            Scene scene = new Scene(nextScene);
            stage.setScene(scene);
            stage.setTitle("MyFishingPal");
            stage.show();
        }
    }

    public boolean checkPassword() throws IOException
    {

        //checks if the username matches any current usernames
        Intermediary currentIntermediary = CSVController.checkIntermediaryExists(usernametxt.getText());
        Fisher currentFisher = CSVController.checkFisherExists(usernametxt.getText());
        //if the username doesnt exist
        if(currentFisher == null && currentIntermediary == null)
        {
            alert.setTitle("User Doesn't Exist");
            alert.setHeaderText("The user doesn't exist");
            alert.showAndWait();
            return false;
        }

        //login for intermediary
        if (currentFisher == null)
        {
            //checks to see if the intermediarys account exists
            if(usernametxt.getText().equalsIgnoreCase(currentIntermediary.getUsername()))
            {
                //checks to see if that user has a password that matches the one they entered
                if(passwordtxt.getText().equals(currentIntermediary.getPassword()))
                {
                    MyFishingPal.currentUser=currentIntermediary;
                    return true;
                } else {
                    System.out.println(passwordtxt.getText() + " " + currentIntermediary.getPassword());
                    alert.setTitle("Incorrect Password");
                    alert.setHeaderText("The password that you have entered is incorrect");
                    alert.showAndWait();
                    return false;
                }
            }

            //login for fisherman
        } else {
            //checks to see if the fishermans account exists
            if(usernametxt.getText().equalsIgnoreCase(currentFisher.getUsername()))
            {
                //checks to see if that user has a password that matches the one they entered
                if(passwordtxt.getText().equals(currentFisher.getPassword()))
                {
                    MyFishingPal.currentUser=currentFisher;
                    return true;
                } else {
                    alert.setTitle("Incorrect Password");
                    alert.setHeaderText("The password that you have entered is incorrect");
                    alert.showAndWait();
                    return false;
                }
            }
        }

        return false;
    }

}
