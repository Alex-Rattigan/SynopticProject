import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.Optional;

public class JobListingsController
{
    @FXML
    private Button helpButton, infoButton, jobListingButton, viewJobsButton, profileButton;

    @FXML
    private TableView<Job> jobListingTable;
    @FXML
    private TableColumn<Job, Integer> jobId;
    @FXML
    private TableColumn<Job, String> fishType;
    @FXML
    private TableColumn<Job, Integer> amount;
    @FXML
    private TableColumn<Job, Date> date;
    @FXML
    private TableColumn<Job, Double> pay;
    @FXML
    private TableColumn<Job, String> managed;

    @FXML
    private Button viewDetailsButton, acceptJobButton;

    LinkedList<Job> availableJobs = new LinkedList<>(DatabaseController.selectJobsWithoutFisher());

    ObservableList<Job> selectedRows;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    public void initialize(){
        createJobList();
        selectedRows.addListener(new ListChangeListener<Job>() {
            @Override
            public void onChanged(Change<? extends Job> change) {
                viewDetailsButton.setDisable(false);
                if(MyFishingPal.currentUser instanceof Fisher){
                    acceptJobButton.setDisable(false);
                }
            }
        });

    }

    public void createJobList(){
        jobId.setCellValueFactory(new PropertyValueFactory<>("id"));
        fishType.setCellValueFactory(new PropertyValueFactory<>("fishType"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amountKg"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateDue"));
        pay.setCellValueFactory(new PropertyValueFactory<>("payPerKg"));
        managed.setCellValueFactory(new PropertyValueFactory<>("intermediaryName"));

        jobListingTable.getItems().addAll(availableJobs);
        TableView.TableViewSelectionModel<Job> availableJobSelectionModel = jobListingTable.getSelectionModel();
        availableJobSelectionModel.setSelectionMode(SelectionMode.SINGLE);
        selectedRows = availableJobSelectionModel.getSelectedItems();
    }

    public void viewJobDetails() throws IOException{
        JobListingsDetailsController.setJobDetails(selectedRows.get(0));

        // Open Job Details page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) viewDetailsButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobListingsDetails.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void acceptJob(){
        if(MyFishingPal.currentUser instanceof Fisher){
            TableView.TableViewSelectionModel<Job> availableJobSelectionModel = jobListingTable.getSelectionModel();
            availableJobSelectionModel.setSelectionMode(SelectionMode.SINGLE);
            selectedRows = availableJobSelectionModel.getSelectedItems();

            Job currentJob = selectedRows.get(0);
            currentJob.setFisherName(((Fisher) MyFishingPal.currentUser).getUsername());

            alert.setTitle("Accept Job");
            alert.setHeaderText("Are you sure you want to accept this job?");
            Optional<ButtonType> result = alert.showAndWait();

            if(!result.isPresent())
            {
                // do nothing
            }
            else if(result.get() == ButtonType.OK)
            {
                DatabaseController.updateFisherId(currentJob.getId(), (((Fisher) MyFishingPal.currentUser).getID()));
                jobListingTable.getItems().clear();
                availableJobs.clear();
                createJobList();
                jobListingTable.getItems().addAll(availableJobs);
                jobListingTable.refresh();

                viewDetailsButton.setDisable(true);
                acceptJobButton.setDisable(true);
            }
            else
            {
                // do nothing
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
