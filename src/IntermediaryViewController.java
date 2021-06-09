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

public class IntermediaryViewController
{
    @FXML
    private Button helpButton, infoButton, jobListingsButton, viewJobsButton, profileButton, viewDetailsAdButton,
            modifyDetailsButton, removeButton, viewDetailsPastButton, viewDetailsAccButton, completeButton;

    @FXML
    private TableView<Job> advertisedJobsTable;
    @FXML
    private TableColumn<Job, Integer> jobIdAdTbl;
    @FXML
    private TableColumn<Job, String> fishTypeAdTbl;
    @FXML
    private TableColumn<Job, Integer> amountAdTbl;
    @FXML
    private TableColumn<Job, Date> dateAdTbl;
    @FXML
    private TableColumn<Job, Double> payAdTbl;

    @FXML
    private TableView<Job> acceptedJobsTable;
    @FXML
    private TableColumn<Job, Integer> jobIdAcceptedTbl;
    @FXML
    private TableColumn<Job, String> fishTypeAcceptedTbl;
    @FXML
    private TableColumn<Job, Integer> amountAcceptedTbl;
    @FXML
    private TableColumn<Job, Date> dateAcceptedTbl;
    @FXML
    private TableColumn<Job, Double> payAcceptedTbl;
    @FXML
    private TableColumn<Job, String> fisherAcceptedTbl;

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
    private TableColumn<Job, String> fisherPastTbl;

    Intermediary currentUser = (Intermediary) MyFishingPal.currentUser;

    LinkedList<Job> jobs = new LinkedList<>();
    LinkedList<Job> adJobs = new LinkedList<>();
    LinkedList<Job> acceptedJobs = new LinkedList<>();
    LinkedList<Job> pastJobs = new LinkedList<>();

    ObservableList<Job> adSelectedRows;
    ObservableList<Job> acceptedSelectedRows;
    ObservableList<Job> pastSelectedRows;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    public void initialize()
    {
        createJobLists();

        jobIdAdTbl.setCellValueFactory(new PropertyValueFactory<>("id"));
        fishTypeAdTbl.setCellValueFactory(new PropertyValueFactory<>("fishType"));
        amountAdTbl.setCellValueFactory(new PropertyValueFactory<>("amountKg"));
        dateAdTbl.setCellValueFactory(new PropertyValueFactory<>("dateDue"));
        payAdTbl.setCellValueFactory(new PropertyValueFactory<>("payPerKg"));

        advertisedJobsTable.getItems().addAll(adJobs);

        TableView.TableViewSelectionModel<Job> adJobsSelectionModel = advertisedJobsTable.getSelectionModel();
        adJobsSelectionModel.setSelectionMode(SelectionMode.SINGLE);
        adSelectedRows = adJobsSelectionModel.getSelectedItems();

        adSelectedRows.addListener(new ListChangeListener<Job>() {
            @Override
            public void onChanged(Change<? extends Job> change) {
                viewDetailsAdButton.setDisable(false);
                modifyDetailsButton.setDisable(false);
                removeButton.setDisable(false);
            }
        });

        jobIdAcceptedTbl.setCellValueFactory(new PropertyValueFactory<>("id"));
        fishTypeAcceptedTbl.setCellValueFactory(new PropertyValueFactory<>("fishType"));
        amountAcceptedTbl.setCellValueFactory(new PropertyValueFactory<>("amountKg"));
        dateAcceptedTbl.setCellValueFactory(new PropertyValueFactory<>("dateDue"));
        payAcceptedTbl.setCellValueFactory(new PropertyValueFactory<>("payPerKg"));
        fisherAcceptedTbl.setCellValueFactory(new PropertyValueFactory<>("fisherName"));

        acceptedJobsTable.getItems().addAll(acceptedJobs);

        TableView.TableViewSelectionModel<Job> acceptedJobsSelectionModel = acceptedJobsTable.getSelectionModel();
        acceptedJobsSelectionModel.setSelectionMode(SelectionMode.SINGLE);
        acceptedSelectedRows = acceptedJobsSelectionModel.getSelectedItems();

        acceptedSelectedRows.addListener(new ListChangeListener<Job>() {
            @Override
            public void onChanged(Change<? extends Job> change) {
                viewDetailsAccButton.setDisable(false);
                completeButton.setDisable(false);
            }
        });

        jobIdPastTbl.setCellValueFactory(new PropertyValueFactory<>("id"));
        fishTypePastTbl.setCellValueFactory(new PropertyValueFactory<>("fishType"));
        amountPastTbl.setCellValueFactory(new PropertyValueFactory<>("amountKg"));
        datePastTbl.setCellValueFactory(new PropertyValueFactory<>("dateDue"));
        payPastTbl.setCellValueFactory(new PropertyValueFactory<>("payPerKg"));
        fisherPastTbl.setCellValueFactory(new PropertyValueFactory<>("fisherName"));

        pastJobsTable.getItems().addAll(pastJobs);

        TableView.TableViewSelectionModel<Job> pastJobsSelectionModel = pastJobsTable.getSelectionModel();
        pastJobsSelectionModel.setSelectionMode(SelectionMode.SINGLE);
        pastSelectedRows = pastJobsSelectionModel.getSelectedItems();

        pastSelectedRows.addListener(new ListChangeListener<Job>() {
            @Override
            public void onChanged(Change<? extends Job> change) {
                viewDetailsPastButton.setDisable(false);
            }
        });

    }

    public void createJobLists()
    {
        jobs = DatabaseController.selectJobByIntermediary(currentUser.getID());

        if(adJobs.size() != 0 || pastJobs.size() != 0 || acceptedJobs.size() != 0)
        {
            adJobs = new LinkedList<>();
            acceptedJobs = new LinkedList<>();
            pastJobs = new LinkedList<>();
        }

        for (Job job : jobs)
        {
            if (!job.isCompleted() && DatabaseController.selectJobReturnFisher(job.getId()) != null)
            {
                job.setFisherName(DatabaseController.selectJobReturnFisher(job.getId()).getFname() + " " + DatabaseController.selectJobReturnFisher(job.getId()).getSname());
                acceptedJobs.add(job);
            }
            else if(job.isCompleted())
            {
                job.setFisherName(DatabaseController.selectJobReturnFisher(job.getId()).getFname() + " " + DatabaseController.selectJobReturnFisher(job.getId()).getSname());
                pastJobs.add(job);
            }
            else
            {
                LinkedList<Job> temp = DatabaseController.selectJobsWithoutFisher();

                for(Job j : temp)
                {
                    if(j.getIntermediaryId() == currentUser.getID())
                    {
                        System.out.println(j.toString() + " BEING ADDED");
                        adJobs.add(j);
                    }
                }
            }
        }

        System.out.println("ADVERTISED JOBS ----------------------------------");
        for(int i = 0; i < adJobs.size(); i++)
        {
            System.out.println(adJobs.get(i));
        }
        System.out.println("ACCEPTED JOBS ----------------------------------");
        for(int i = 0; i < acceptedJobs.size(); i++)
        {
            System.out.println(acceptedJobs.get(i));
        }
        System.out.println("PAST JOBS ----------------------------------");
        for(int i = 0; i < pastJobs.size(); i++)
        {
            System.out.println(pastJobs.get(i));
        }
    }

    public void viewAdJobDetails() throws IOException
    {
        JobDetailsController.setJobDetails(adSelectedRows.get(0));

        // Open Job Details page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) viewDetailsAdButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobDetails.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void modifyJobDetails() throws IOException
    {
        JobDetailsController.setJobDetails(adSelectedRows.get(0));

        // Open Job Details page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) modifyDetailsButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobDetails.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void removeJob() throws IOException
    {
        JobDetailsController.setJobDetails(adSelectedRows.get(0));

        // Open Job Details page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) removeButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobDetails.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void viewJobDetails() throws IOException
    {
        JobDetailsController.setJobDetails(acceptedSelectedRows.get(0));

        // Open Job Details page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) viewDetailsAccButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobDetails.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void markJobComplete()
    {
        Job job = acceptedSelectedRows.get(0);

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
            acceptedJobsTable.getItems().clear();
            pastJobsTable.getItems().clear();
            createJobLists();
            acceptedJobsTable.getItems().addAll(acceptedJobs);
            pastJobsTable.getItems().addAll(pastJobs);
            acceptedJobsTable.refresh();
            pastJobsTable.refresh();

            viewDetailsAccButton.setDisable(true);
            completeButton.setDisable(true);
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
        } else if(event.getSource() == jobListingsButton){
            stage = (Stage) jobListingsButton.getScene().getWindow();
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
