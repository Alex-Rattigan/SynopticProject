/*******************************************************************************************************************
 * File: IntermediaryViewController.java
 *
 * Date: 09/06/2021
 *
 * Author: RM
 *
 * Description: This class is for controlling the GUI users of type Intermediary see when they click the 'View Jobs'
 *              button from the toolbar in the MyFishingPal application. It contains a number of tables which update
 *              with specific information from the database or local CSVs.
 *
 * References: [1] http://tutorials.jenkov.com/javafx/tableview.html
 *             [2] https://stackoverflow.com/questions/43031602/how-to-set-a-method-to-a-javafx-alert-button
 *             [3] https://stackoverflow.com/questions/32176782/how-can-i-clear-the-all-contents-of-the-cell-data-in-every-row-in-my-tableview-i/52770465
 *             [4] Change scene code from Richey Blant & Ruby Moore's Software Engineering Coursework, provided by
 *  *             Richey Blant
 *
 ******************************************************************************************************************/


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
import java.util.LinkedList;
import java.util.Optional;

public class IntermediaryViewController
{
    @FXML
    private Button helpButton, infoButton, jobListingsButton, viewJobsButton, profileButton, viewDetailsAdButton,
            modifyDetailsButton, removeButton, viewDetailsPastButton, viewDetailsAccButton, completeButton,
            createJobButton;

    @FXML
    private TableView<Job> advertisedJobsTable;
    @FXML
    private TableColumn<Job, Integer> jobIdAdTbl;
    @FXML
    private TableColumn<Job, String> fishTypeAdTbl;
    @FXML
    private TableColumn<Job, Integer> amountAdTbl;
    @FXML
    private TableColumn<Job, String> dateAdTbl;
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
    private TableColumn<Job, String> dateAcceptedTbl;
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
    private TableColumn<Job, String> datePastTbl;
    @FXML
    private TableColumn<Job, Double> payPastTbl;
    @FXML
    private TableColumn<Job, String> fisherPastTbl;

    // Get user currently logged in
    Intermediary currentUser = (Intermediary) MyFishingPal.currentUser;

    // Lists of all job types
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
        // initialise the job lists of different types
        createJobLists();

        // put advertised jobs into their respective table
        jobIdAdTbl.setCellValueFactory(new PropertyValueFactory<>("id"));
        fishTypeAdTbl.setCellValueFactory(new PropertyValueFactory<>("fishType"));
        amountAdTbl.setCellValueFactory(new PropertyValueFactory<>("amountKg"));
        dateAdTbl.setCellValueFactory(new PropertyValueFactory<>("formattedDueDate"));
        payAdTbl.setCellValueFactory(new PropertyValueFactory<>("payPerKg"));

        advertisedJobsTable.getItems().addAll(adJobs);

        // set selection model so a single row can be selected at a time
        TableView.TableViewSelectionModel<Job> adJobsSelectionModel = advertisedJobsTable.getSelectionModel();
        adJobsSelectionModel.setSelectionMode(SelectionMode.SINGLE);
        adSelectedRows = adJobsSelectionModel.getSelectedItems();

        // enable the buttons on the page once a row has been selected
        adSelectedRows.addListener(new ListChangeListener<Job>() {
            @Override
            public void onChanged(Change<? extends Job> change) {
                viewDetailsAdButton.setDisable(false);
                modifyDetailsButton.setDisable(false);
                removeButton.setDisable(false);
            }
        });

        // put accepted jobs into their table
        jobIdAcceptedTbl.setCellValueFactory(new PropertyValueFactory<>("id"));
        fishTypeAcceptedTbl.setCellValueFactory(new PropertyValueFactory<>("fishType"));
        amountAcceptedTbl.setCellValueFactory(new PropertyValueFactory<>("amountKg"));
        dateAcceptedTbl.setCellValueFactory(new PropertyValueFactory<>("formattedDueDate"));
        payAcceptedTbl.setCellValueFactory(new PropertyValueFactory<>("payPerKg"));
        fisherAcceptedTbl.setCellValueFactory(new PropertyValueFactory<>("fisherName"));

        acceptedJobsTable.getItems().addAll(acceptedJobs);

        // set selection model so a single row can be selected at a time
        TableView.TableViewSelectionModel<Job> acceptedJobsSelectionModel = acceptedJobsTable.getSelectionModel();
        acceptedJobsSelectionModel.setSelectionMode(SelectionMode.SINGLE);
        acceptedSelectedRows = acceptedJobsSelectionModel.getSelectedItems();

        // enable buttons on row selection
        acceptedSelectedRows.addListener(new ListChangeListener<Job>() {
            @Override
            public void onChanged(Change<? extends Job> change) {
                viewDetailsAccButton.setDisable(false);
                completeButton.setDisable(false);
            }
        });

        // put completed jobs in table
        jobIdPastTbl.setCellValueFactory(new PropertyValueFactory<>("id"));
        fishTypePastTbl.setCellValueFactory(new PropertyValueFactory<>("fishType"));
        amountPastTbl.setCellValueFactory(new PropertyValueFactory<>("amountKg"));
        datePastTbl.setCellValueFactory(new PropertyValueFactory<>("formattedDueDate"));
        payPastTbl.setCellValueFactory(new PropertyValueFactory<>("payPerKg"));
        fisherPastTbl.setCellValueFactory(new PropertyValueFactory<>("fisherName"));

        pastJobsTable.getItems().addAll(pastJobs);

        // set selection model so a single row can be selected at a time
        TableView.TableViewSelectionModel<Job> pastJobsSelectionModel = pastJobsTable.getSelectionModel();
        pastJobsSelectionModel.setSelectionMode(SelectionMode.SINGLE);
        pastSelectedRows = pastJobsSelectionModel.getSelectedItems();

        // enable button on row selection
        pastSelectedRows.addListener(new ListChangeListener<Job>() {
            @Override
            public void onChanged(Change<? extends Job> change) {
                viewDetailsPastButton.setDisable(false);
            }
        });

    }

    // Open create job page
    public void createNewJob() throws IOException {
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) createJobButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("CreateJob.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    // Initialise job lists
    public void createJobLists()
    {
        jobs = CSVController.selectJobsByIntermediary(currentUser.getID()); // get all jobs from current user

        // refresh lists
        if(adJobs.size() != 0 || pastJobs.size() != 0 || acceptedJobs.size() != 0)
        {
            adJobs = new LinkedList<>();
            acceptedJobs = new LinkedList<>();
            pastJobs = new LinkedList<>();
        }

        for (Job job : jobs)
        {
            if (!job.isCompleted() && CSVController.selectJobReturnFisher(job.getId()) != null) // check if a job is accepted
            {
                job.setFisherName(CSVController.selectJobReturnFisher(job.getId()).getFname() + " " + CSVController.selectJobReturnFisher(job.getId()).getSname());
                acceptedJobs.add(job);
            }
            else if(job.isCompleted()) // completed jobs
            {
                job.setFisherName(CSVController.selectJobReturnFisher(job.getId()).getFname() + " " + CSVController.selectJobReturnFisher(job.getId()).getSname());
                pastJobs.add(job);
            }
            else // job isn't completed or accepted, so add to advertised jobs
            {
                LinkedList<Job> temp = CSVController.selectJobsWithoutFisher();

                for(Job j : temp)
                {
                    if(j.getIntermediaryId() == currentUser.getID() && j.getId() == job.getId())
                    {
                        adJobs.add(j);
                    }
                }
            }
        }
    }

    // view details of a job on the advertised page
    public void viewAdJobDetails() throws IOException
    {
        // pass job details to JobDetailsIntermediary Controller
        JobDetailsIntermediaryController.setJobDetails(adSelectedRows.get(0));
        JobDetailsIntermediaryController.setIsFromModifyButton(false);

        // Open Job Details page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) viewDetailsAdButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobDetailsIntermediary.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void modifyJobDetails() throws IOException
    {
        // pass job details to JobDetailsIntermediary Controller
        JobDetailsIntermediaryController.setJobDetails(adSelectedRows.get(0));
        JobDetailsIntermediaryController.setIsFromModifyButton(true);

        // Open Job Details page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) modifyDetailsButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobDetailsIntermediary.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void removeJob() throws IOException
    {
        // show confirmation pop-up
        alert.setTitle("Remove Job");
        alert.setHeaderText("Are you sure you want to remove this job?");
        Optional<ButtonType> result = alert.showAndWait();

        if(!result.isPresent())
        {
            // do nothing
        }
        else if(result.get() == ButtonType.OK)
        {
            CSVController.deleteJob(adSelectedRows.get(0).getId());

            // refresh tables
            advertisedJobsTable.getItems().clear();
            createJobLists();
            advertisedJobsTable.getItems().addAll(adJobs);
            advertisedJobsTable.refresh();

            // re-disable buttons
            viewDetailsAdButton.setDisable(true);
            removeButton.setDisable(true);
            modifyDetailsButton.setDisable(true);
        }
        else
        {
            // do nothing
        }
    }

    public void viewAcceptedJobDetails() throws IOException
    {
        // pass job details to JobDetailsIntermediary Controller
        JobDetailsIntermediaryController.setJobDetails(acceptedSelectedRows.get(0));
        JobDetailsIntermediaryController.setIsFromModifyButton(false);

        // Open Job Details page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) viewDetailsAccButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobDetailsIntermediary.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void viewPastJobDetails() throws IOException
    {
        // pass job details to JobDetailsIntermediary Controller
        JobDetailsIntermediaryController.setJobDetails(pastSelectedRows.get(0));
        JobDetailsIntermediaryController.setIsFromModifyButton(false);

        // Open Job Details page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) viewDetailsPastButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobDetailsIntermediary.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void markJobComplete()
    {
        Job job = acceptedSelectedRows.get(0);

        // show confirmation pop-up
        alert.setTitle("Mark Job as Completed");
        alert.setHeaderText("Are you sure you want to mark this job completed?");
        Optional<ButtonType> result = alert.showAndWait();

        if(!result.isPresent())
        {
            // do nothing
        }
        else if(result.get() == ButtonType.OK)
        {
            CSVController.updateCompleted(job.getId(), true);

            // refresh tables to show complete item
            acceptedJobsTable.getItems().clear();
            pastJobsTable.getItems().clear();
            createJobLists();
            acceptedJobsTable.getItems().addAll(acceptedJobs);
            pastJobsTable.getItems().addAll(pastJobs);
            acceptedJobsTable.refresh();
            pastJobsTable.refresh();

            // re-disable buttons
            viewDetailsAccButton.setDisable(true);
            completeButton.setDisable(true);
        }
        else
        {
            // do nothing
        }
    }

    // tool bar
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
