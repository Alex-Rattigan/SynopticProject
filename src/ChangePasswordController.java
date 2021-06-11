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
    private Button closeButton;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void changePassword() throws IOException{
        if(MyFishingPal.currentUser instanceof Intermediary) {
            Intermediary i = CSVController.selectIntermediaryRecord(((Intermediary) MyFishingPal.currentUser).getID());
            String currentPassword0 = i.getPassword();
            String currentPassword1 = currentPassword.getText();
            String newPass0 = newPassword0.getText();
            String newPass1 = newPassword1.getText();
            if (currentPassword0.equals(currentPassword1) && newPass0.equals(newPass1)){
                CSVController.updatePasswordIntermediary(((Intermediary) MyFishingPal.currentUser).getID(), newPass0);
                alert.setTitle("Successfully Changed Password");
                alert.setHeaderText("The password has successfully been changed");
                alert.showAndWait();
            }else {
                alert.setTitle("Incorrect passwords");
                alert.setHeaderText("Either the current password did not match our records, or the new passwords weren't the same");
                alert.showAndWait();
            }
        } else if(MyFishingPal.currentUser instanceof Fisher){
            Fisher f = CSVController.selectFisherRecord(((Fisher) MyFishingPal.currentUser).getID());
            String currentPassword0 = f.getPassword();
            String currentPassword1 = currentPassword.getText();
            String newPass0 = newPassword0.getText();
            String newPass1 = newPassword1.getText();
            if (currentPassword0.equals(currentPassword1) && newPass0.equals(newPass1)){
                CSVController.updatePasswordFisher(((Fisher) MyFishingPal.currentUser).getID(), newPass0);
                alert.setTitle("Successfully Changed Password");
                alert.setHeaderText("The password has successfully been changed");
                alert.showAndWait();
            }else {
                alert.setTitle("Incorrect passwords");
                alert.setHeaderText("Either the current password did not match our records, or the new passwords weren't the same");
                alert.showAndWait();
            }
        }
    }

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
