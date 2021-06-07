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

    @FXML
    private TextField currentPassword, newPassword0, newPassword1;
    @FXML
    private Button confirmButton;
    @FXML
    private Button helpButton, infoButton, jobListingButton, viewJobsButton, profileButton;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void changePassword() throws IOException{
        if(MyFishingPal.currentUser instanceof Intermediary) {
            Intermediary i = DatabaseController.selectIntermediaryRecord(((Intermediary) MyFishingPal.currentUser).getID());
            String currentPassword0 = i.getPassword();
            String currentPassword1 = currentPassword.getText();
            String newPass0 = newPassword0.getText();
            String newPass1 = newPassword1.getText();
            if (currentPassword0.equals(currentPassword1) && newPass0.equals(newPass1)){
                DatabaseController.updatePasswordIntermediary(((Intermediary) MyFishingPal.currentUser).getID(), newPass0);
                Stage stage = null;
                Parent nextScene = null;
                stage = (Stage) confirmButton.getScene().getWindow();
                nextScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));
                assert nextScene != null;
                Scene scene = new Scene(nextScene);
                stage.setScene(scene);
                stage.setTitle("MyFishingPal");
                stage.show();
            }else {
                alert.setTitle("Incorrect passwords");
                alert.setHeaderText("Either the current password did not match our records, or the new passwords weren't the same");
                alert.showAndWait();
            }
        } else if(MyFishingPal.currentUser instanceof Fisher){
            Fisher f = DatabaseController.selectFisherRecord(((Fisher) MyFishingPal.currentUser).getID());
            String currentPassword0 = f.getPassword();
            String currentPassword1 = currentPassword.getText();
            String newPass0 = newPassword0.getText();
            String newPass1 = newPassword1.getText();
            if (currentPassword0.equals(currentPassword1) && newPass0.equals(newPass1)){
                DatabaseController.updatePasswordFisher(((Fisher) MyFishingPal.currentUser).getID(), newPass0);
                Stage stage = null;
                Parent nextScene = null;
                stage = (Stage) confirmButton.getScene().getWindow();
                nextScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));
                assert nextScene != null;
                Scene scene = new Scene(nextScene);
                stage.setScene(scene);
                stage.setTitle("MyFishingPal");
                stage.show();
            }else {
                alert.setTitle("Incorrect passwords");
                alert.setHeaderText("Either the current password did not match our records, or the new passwords weren't the same");
                alert.showAndWait();
            }
        }
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

        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.show();
    }

}
