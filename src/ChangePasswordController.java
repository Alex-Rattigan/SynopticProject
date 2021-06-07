import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePasswordController {

    @FXML
    private TextField currentPassword, newPassword0, newPassword1;
    @FXML
    private Button confirmButton;
    @FXML
    private Button helpButton, infoButton, jobListingButton, viewJobsButton, profileButton;

    public void changePassword(){
        //if Object instanceOf Intermediary
            //Intermediary i = DatabaseController.selectIntermediaryRecord(currentUserId);
            //String currentPassword0 = i.getPassword();
            //String currentPassword1 = currentPassword.getText();
            //String newPassword0 = newPassword0.getText();
            //String newPassword1 = newPassword1.getText();
            //if(currentPassword0.equals(currentPassword1) && newPassword0.equals(newPassword1))
                //DatabaseController.updatePasswordIntermediary(currentUserId, newPassword0);
                //Stage stage;
                //Parent nextScene;
                //stage = (Stage) confirmButton.getScene().getWindow();
                //nextScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));
            //else
                //alert.setTitle("Incorrect passwords");
                //alert.setHeaderText("Either the current password did not match our records, or the new passwords weren't the same");
                //alert.showAndWait();
        //else if Object instanceOf Fisher
            //Fisher f = DatabaseController.selectFisherRecord(currentUserId);
            //String currentPassword0 = f.getPassword();
            //String currentPassword1 = currentPassword.getText();
            //String newPassword0 = newPassword0.getText();
            //String newPassword1 = newPassword1.getText();
            //if(currentPassword0.equals(currentPassword1) && newPassword0.equals(newPassword1))
                //DatabaseController.updatePasswordFisher(currentUserId, newPassword0);
                //Stage stage;
                //Parent nextScene;
                //stage = (Stage) confirmButton.getScene().getWindow();
                //nextScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));
            //else
                //alert.setTitle("Incorrect passwords");
                //alert.setHeaderText("Either the current password did not match our records, or the new passwords weren't the same");
                //alert.showAndWait();
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
