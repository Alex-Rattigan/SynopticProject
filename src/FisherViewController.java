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

// References: http://tutorials.jenkov.com/javafx/tableview.html
//             https://stackoverflow.com/questions/43031602/how-to-set-a-method-to-a-javafx-alert-button
//             https://stackoverflow.com/questions/32176782/how-can-i-clear-the-all-contents-of-the-cell-data-in-every-row-in-my-tableview-i/52770465

public class FisherViewController
{
    @FXML
    private Button helpButton, infoButton, jobListingButton, viewJobsButton, profileButton, viewDetailsButton, viewDetailsButton1, completeButton, abandonButton;

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

    LinkedList<Job> jobs = new LinkedList<>();
    LinkedList<Job> activeJobs = new LinkedList<>();
    LinkedList<Job> pastJobs = new LinkedList<>();

    ObservableList<Job> activeSelectedRows;
    ObservableList<Job> pastSelectedRows;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    public void initialize()
    {
        createJobLists();

        jobIdActiveTbl.setCellValueFactory(new PropertyValueFactory<>("id"));
        fishTypeActiveTbl.setCellValueFactory(new PropertyValueFactory<>("fishType"));
        amountActiveTbl.setCellValueFactory(new PropertyValueFactory<>("amountKg"));
        dateActiveTbl.setCellValueFactory(new PropertyValueFactory<>("dateDue"));
        payActiveTbl.setCellValueFactory(new PropertyValueFactory<>("payPerKg"));
        managedActiveTbl.setCellValueFactory(new PropertyValueFactory<>("intermediaryName"));

        activeJobsTable.getItems().addAll(activeJobs);

        TableView.TableViewSelectionModel<Job> activeJobsSelectionModel = activeJobsTable.getSelectionModel();
        activeJobsSelectionModel.setSelectionMode(SelectionMode.SINGLE);
        activeSelectedRows = activeJobsSelectionModel.getSelectedItems();

        activeSelectedRows.addListener(new ListChangeListener<Job>() {
            @Override
            public void onChanged(Change<? extends Job> change) {
                viewDetailsButton.setDisable(false);
                completeButton.setDisable(false);
                abandonButton.setDisable(false);
            }
        });


        jobIdPastTbl.setCellValueFactory(new PropertyValueFactory<>("id"));
        fishTypePastTbl.setCellValueFactory(new PropertyValueFactory<>("fishType"));
        amountPastTbl.setCellValueFactory(new PropertyValueFactory<>("amountKg"));
        datePastTbl.setCellValueFactory(new PropertyValueFactory<>("dateDue"));
        payPastTbl.setCellValueFactory(new PropertyValueFactory<>("payPerKg"));
        managedPastTbl.setCellValueFactory(new PropertyValueFactory<>("intermediaryName"));

        pastJobsTable.getItems().addAll(pastJobs);

        TableView.TableViewSelectionModel<Job> pastJobsSelectionModel = pastJobsTable.getSelectionModel();
        pastJobsSelectionModel.setSelectionMode(SelectionMode.SINGLE);
        pastSelectedRows = pastJobsSelectionModel.getSelectedItems();

        pastSelectedRows.addListener(new ListChangeListener<Job>() {
            @Override
            public void onChanged(Change<? extends Job> change) {
                viewDetailsButton1.setDisable(false);
            }
        });

    }

    public void createJobLists()
    {
        jobs = DatabaseController.selectJobsByFisher(currentUser.getID());

        if(activeJobs.size() != 0 || pastJobs.size() != 0)
        {
            activeJobs = new LinkedList<>();
            pastJobs = new LinkedList<>();
        }

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
    }

    public void viewActiveJobDetails() throws IOException
    {
        JobDetailsController.setJobDetails(activeSelectedRows.get(0));

        // Open Job Details page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) viewDetailsButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobDetails.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void viewPastJobDetails() throws IOException
    {
        JobDetailsController.setJobDetails(pastSelectedRows.get(0));

        // Open Job Details page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) viewDetailsButton1.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobDetails.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void markJobComplete()
    {
        Job job = activeSelectedRows.get(0);

        alert.setTitle("Mark Job as Completed");
        alert.setHeaderText("Are you sure you want to mark this job completed?");
        Optional<ButtonType> result = alert.showAndWait();

        if(!result.isPresent())
        {
            // do nothing
        }
        else if(result.get() == ButtonType.OK)
        {
            DatabaseController.updateCompleted(job.getId(), true);
            activeJobsTable.getItems().clear();
            pastJobsTable.getItems().clear();
            createJobLists();
            activeJobsTable.getItems().addAll(activeJobs);
            pastJobsTable.getItems().addAll(pastJobs);
            activeJobsTable.refresh();
            pastJobsTable.refresh();

            viewDetailsButton.setDisable(true);
            viewDetailsButton1.setDisable(true);
            completeButton.setDisable(true);
        }
        else
        {
            // do nothing
        }
    }

    public void abandonJob()
    {
        Job job = activeSelectedRows.get(0);

        alert.setTitle("Abandon Job");
        alert.setHeaderText("Are you sure you want to abandon this job?");
        Optional<ButtonType> result = alert.showAndWait();

        if(!result.isPresent())
        {
            // do nothing
        }
        else if(result.get() == ButtonType.OK)
        {
            DatabaseController.updateFisherId(job.getId(), null);
            activeJobsTable.getItems().clear();
            pastJobsTable.getItems().clear();
            createJobLists();
            activeJobsTable.getItems().addAll(activeJobs);
            pastJobsTable.getItems().addAll(pastJobs);
            activeJobsTable.refresh();
            pastJobsTable.refresh();

            viewDetailsButton.setDisable(true);
            viewDetailsButton1.setDisable(true);
            abandonButton.setDisable(true);
        }
        else
        {
            // do nothing
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
