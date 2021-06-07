import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;

public class FisherViewController
{
    @FXML
    private Button helpButton, infoButton, jobListingButton, viewJobsButton, profileButton;

    @FXML
    private TableView<Job> activeJobsTable;
    @FXML
    private TableColumn<Job, Integer> jobIdActiveTbl;
    @FXML
    private TableColumn<Job, String> fishTypeActiveTbl;
    @FXML
    private TableColumn<Job, Integer> amountActiveTbl;
    @FXML
    private TableColumn<Job, Date> dateActiveTbl;
    @FXML
    private TableColumn<Job, Double> payActiveTbl;
    @FXML
    private TableColumn<Job, String> managedActiveTbl;

    @FXML
    private TableView<Job> pastJobsTable;
    @FXML
    private TableColumn<Job, Integer> jobIdPastTbl;
    @FXML
    private TableColumn<Job, String> fishTypePastTbl;
    @FXML
    private TableColumn<Job, Integer> amountPastTbl;
    @FXML
    private TableColumn<Job, Date> datePastTbl;
    @FXML
    private TableColumn<Job, Double> payPastTbl;
    @FXML
    private TableColumn<Job, String> managedPastTbl;

    Fisher currentUser = (Fisher) MyFishingPal.currentUser;

    LinkedList<Job> jobs = new LinkedList<>(DatabaseController.selectJobsByFisher(currentUser.getID()));
    LinkedList<Job> activeJobs = new LinkedList<>();
    LinkedList<Job> pastJobs = new LinkedList<>();

    public void initialize()
    {
        for (Job job : jobs)
        {
            if (!job.isCompleted())
            {
                activeJobs.add(job);
            }
            else
            {
                pastJobs.add(job);
            }
        }

        jobIdActiveTbl.setCellValueFactory(new PropertyValueFactory<>("id"));
        fishTypeActiveTbl.setCellValueFactory(new PropertyValueFactory<>("fishType"));
        amountActiveTbl.setCellValueFactory(new PropertyValueFactory<>("amountKg"));
        dateActiveTbl.setCellValueFactory(new PropertyValueFactory<>("dateDue"));
        payActiveTbl.setCellValueFactory(new PropertyValueFactory<>("payPerKg"));
        managedActiveTbl.setCellValueFactory(new PropertyValueFactory<>("intermediaryName"));

        activeJobsTable.getItems().addAll(activeJobs);

        jobIdPastTbl.setCellValueFactory(new PropertyValueFactory<>("id"));
        fishTypePastTbl.setCellValueFactory(new PropertyValueFactory<>("fishType"));
        amountPastTbl.setCellValueFactory(new PropertyValueFactory<>("amountKg"));
        datePastTbl.setCellValueFactory(new PropertyValueFactory<>("dateDue"));
        payPastTbl.setCellValueFactory(new PropertyValueFactory<>("payPerKg"));
        managedPastTbl.setCellValueFactory(new PropertyValueFactory<>("intermediaryName"));

        pastJobsTable.getItems().addAll(pastJobs);
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
