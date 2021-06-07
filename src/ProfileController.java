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

    @FXML
    private TextField userForename, userSurname, userNumber;
    @FXML
    private Button changePasswordButton, changeNumberButton, logoutButton;
    @FXML
    private Button helpButton, infoButton, jobListingButton, viewJobsButton, profileButton;

    public void initialize(){
        displayInfo();
    }

    public void displayInfo(){
        //if Object instanceOf Intermediary
            //Intermediary i = DatabaseController.selectIntermediaryRecord(currentUserId);
            //userForename.setText(i.getFname())
            //userSurname.setText(i.getSname())
            //userNumber.setText(i.getMobileNo)
        //else if Object instanceOf Fisher
            //Fisher f = DatabaseController.selectFisherRecord(currentUserId);
            //userForename.setText(f.getFname)
            //userSurname.setText(f.getSname)
            //userNumber.setText(f.getMobileNo)
    }

    public void changePassword(){
        //if current user is present in intermediary
            //Stage stage;
            //Parent nextScene;
            //stage = (Stage) changePasswordButton.getScene().getWindow();
            //nextScene = FXMLLoader.load(getClass().getResource("ChangePassword.fxml");
            //assert nextScene != null;
            //Scene scene = new Scene(nextScene);
            //stage.setScene(scene);
            //stage.setTitle("MyFishingPal");
            //stage.show();
        //else if current user is present in fisher
            //Stage stage;
            //Parent nextScene;
            //stage = (Stage) changePasswordButton.getScene().getWindow();
            //nextScene = FXMLLoader.load(getClass().getResource("ChangePassword.fxml");
            //assert nextScene != null;
            //Scene scene = new Scene(nextScene);
            //stage.setScene(scene);
            //stage.setTitle("MyFishingPal");
            //stage.show();
    }

    public void changeNumber(){
        //if current user is present in intermediary
            //Stage stage;
            //Parent nextScene;
            //stage = (Stage) changeNumberButton.getScene().getWindow();
            //nextScene = FXMLLoader.load(getClass().getResource("ChangeMobNo.fxml");
            //assert nextScene != null;
            //Scene scene = new Scene(nextScene);
            //stage.setScene(scene);
            //stage.setTitle("MyFishingPal");
            //stage.show();
        //else if current user is present in fisher
            //Stage stage;
            //Parent nextScene;
            //stage = (Stage) changeNumberButton.getScene().getWindow();
            //nextScene = FXMLLoader.load(getClass().getResource("ChangeMobNo.fxml");
            //assert nextScene != null;
            //Scene scene = new Scene(nextScene);
            //stage.setScene(scene);
            //stage.setTitle("MyFishingPal");
            //stage.show();
    }

    public void logOut() throws IOException{
        MyFishingPal.currentUser = null;
        Stage stage;
        Parent nextScene;
        stage = (Stage) logoutButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("Login.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void changeScene(ActionEvent event) throws IOException{
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
