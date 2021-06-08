import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelpController {

    @FXML
    private Button jobViewButton0, jobViewButton1, jobViewButton2, jobViewButton3, jobViewButton4,
            jobViewButton5, jobViewButton6, jobViewButton7, jobViewButton8;

    @FXML
    private Button jobListingButton0, jobListingButton1, fishingInfoButton, profileButton0, profileButton1,
            profileButton2;

    @FXML
    private Button helpButton, infoButton, jobListingButton, viewJobsButton, profileButton;

    public void goToJobView() throws IOException{
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) viewJobsButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobListings.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void goToJobListing() throws IOException{
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) jobListingButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobListings.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void goToProfile() throws IOException{
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) profileButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void goToFishingInfo() throws IOException{
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) fishingInfoButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("FishingInfo.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
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

            if(MyFishingPal.currentUser instanceof Fisher)
            {
                nextScene = FXMLLoader.load(getClass().getResource("FisherView.fxml"));
            }
            else
            {
                nextScene = FXMLLoader.load(getClass().getResource("IntermediaryView.fxml"));
            }
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
