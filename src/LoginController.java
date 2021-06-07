import javafx.event.ActionEvent;
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
        //test data
        //DatabaseController.insertFisherRecord("james9201","pass","james","rod","07308247748");
        //DatabaseController.insertIntermediaryRecord("frank92041","test","james","rod","07309247748");

        //checks if the username matches any current usernames
        Intermediary currentIntermediary = DatabaseController.checkIntermediaryExists(usernametxt.getText());
        Fisher currentFisher = DatabaseController.checkFisherExists(usernametxt.getText());
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
        {//if intermediary
            if(usernametxt.getText().equalsIgnoreCase(currentIntermediary.getUsername())) //if username entered == username in the database
            {
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
            if(usernametxt.getText().equalsIgnoreCase(currentFisher.getUsername()))
            {
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
