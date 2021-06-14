/*******************************************************************************************************************
 * File: JobListingsController.java
 *
 * Date: 8/6/2021
 *
 * Author: Richey Blant
 *
 * Description: This is the controller that adds functionality to the JobListings.fxml page, this page shows the
 * available jobs for the fishers and allows them to accept the jobs that they are wanting to take.
 *
 ******************************************************************************************************************/

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
    //these are the variable definitions for the table and the columns
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
    //these are the variable definitions for the page buttons
    @FXML
    private Button viewDetailsButton, acceptJobButton;
    //these are the variable definitions for the navigation buttons
    @FXML
    private Button helpButton, infoButton, jobListingButton, viewJobsButton, profileButton;

    //this creates a list of the available jobs
    LinkedList<Job> availableJobs = new LinkedList<>();

    //this observes what row is selected for the user to be able to accept a job
    ObservableList<Job> selectedRows;

    //this creates the alert that gets used in this class
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    //this is the method that runs as soon as the page is loaded
    public void initialize(){
        //runs the createJobList method
        createJobList();

        //this sets the information to the table to be viewed
        jobId.setCellValueFactory(new PropertyValueFactory<>("id"));
        fishType.setCellValueFactory(new PropertyValueFactory<>("fishType"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amountKg"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateDue"));
        pay.setCellValueFactory(new PropertyValueFactory<>("payPerKg"));
        managed.setCellValueFactory(new PropertyValueFactory<>("intermediaryName"));

        //this adds all the available jobs to the job listing table to be viewed
        jobListingTable.getItems().addAll(availableJobs);
        //this is to select the table row that is wanting to be chosen for either more information or for accepting a job
        TableView.TableViewSelectionModel<Job> availableJobSelectionModel = jobListingTable.getSelectionModel();
        availableJobSelectionModel.setSelectionMode(SelectionMode.SINGLE);
        selectedRows = availableJobSelectionModel.getSelectedItems();

        //this is what listens to the selection
        selectedRows.addListener(new ListChangeListener<Job>() {
            @Override
            //once someone has selected a job, the buttons will become available
            public void onChanged(Change<? extends Job> change) {
                viewDetailsButton.setDisable(false);
                if(MyFishingPal.currentUser instanceof Fisher){
                    acceptJobButton.setDisable(false);
                }
            }
        });

    }

    //this is the create job list method, this gets all the jobs that don't have an active fisher
    public void createJobList(){
        //puts these jobs into the available jobs list
        availableJobs = CSVController.selectJobsWithoutFisher();
    }

    //this is the method that goes to the view details page, this just gives the user a better look at the job
    //where it isn't in table view
    public void viewJobDetails() throws IOException{
        //sets the job that it wants to view to the selected job
        JobListingsDetailsController.setJobDetails(selectedRows.get(0));

        //changes scenes to the job details page
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

    //this is the method that lets the user accept a job
    public void acceptJob(){
        //checks to make sure the user is a fisher
        if(MyFishingPal.currentUser instanceof Fisher){
            //this is to select the table row that is wanting to be chosen for either more information or for accepting a job
            TableView.TableViewSelectionModel<Job> availableJobSelectionModel = jobListingTable.getSelectionModel();
            availableJobSelectionModel.setSelectionMode(SelectionMode.SINGLE);
            selectedRows = availableJobSelectionModel.getSelectedItems();
            //this sets the current job to the one that is selected
            Job currentJob = selectedRows.get(0);

            //Check if the selected job is still available
            LinkedList<Job> availableJobsLive = DatabaseController.selectJobsWithoutFisher();
            boolean currentJobLive = false;
            assert availableJobsLive != null;
            for (Job job : availableJobsLive) {

                if (job.getId() == currentJob.getId()) {

                    currentJobLive = true;
                    break;

                }

            }

            //If job is available, proceed as normal
            if (currentJobLive) {

                //sets the currently logged in user as the fisher for the job that is selected
                currentJob.setFisherName(((Fisher) MyFishingPal.currentUser).getUsername());

                //this puts up a warning to ask if the user is sure about accepting the job
                alert.setTitle("Accept Job");
                alert.setHeaderText("Are you sure you want to accept this job?");
                Optional<ButtonType> result = alert.showAndWait();

                //Email the Fisher the details of the job and the Intermediary that offered it
                Intermediary intermediary = CSVController.selectIntermediaryRecord(currentJob.getIntermediaryId());
                assert intermediary != null;
                String intermediaryPhone = intermediary.getMobileNo();
                String message =    "Job accepted through MyFishingPal:\n" +
                        "Intermediary Name: " + currentJob.getIntermediaryName() + "\n" +
                        "Intermediary Contact Number: " + intermediaryPhone + "\n" +
                        "Fish Type: " + currentJob.getFishType() + "\n" +
                        "Amount Required: " + currentJob.getAmountKg() + "Kg\n" +
                        "Pay Rate: " + currentJob.getPayPerKg() + "Sol/Kg\n" +
                        "Due Date: " + currentJob.getDateDue() + "\n" +
                        "Description: " + currentJob.getDescription();
                new Email(((Fisher) MyFishingPal.currentUser).getMobileNo(), "You Have Accepted Job #" + currentJob.getId() + " Through MyFishingPal", message);

                //Email the Intermediary the details of the job and the Fisher that accepted
                message =   "Job accepted through MyFishingPal:\n" +
                        "Fisher Name: " + ((Fisher) MyFishingPal.currentUser).getFullName() + "\n" +
                        "Fisher Contact Number: " + ((Fisher) MyFishingPal.currentUser).getMobileNo() + "\n" +
                        "Fish Type: " + currentJob.getFishType() + "\n" +
                        "Amount Required: " + currentJob.getAmountKg() + "Kg\n" +
                        "Pay Rate: " + currentJob.getPayPerKg() + "Sol/Kg\n" +
                        "Due Date: " + currentJob.getDateDue() + "\n" +
                        "Description: " + currentJob.getDescription();
                new Email(intermediary.getMobileNo(), "Your Job #" + currentJob.getId() + " Has Been Accepted Through MyFishingPal", message);

                if(!result.isPresent())
                {
                    // do nothing
                }
                else if(result.get() == ButtonType.OK)
                {
                    //update the fisher id for a job to the current user
                    CSVController.updateFisherId(currentJob.getId(), (((Fisher) MyFishingPal.currentUser).getID()));

                    //this clears the table, fills the table again
                    jobListingTable.getItems().clear();
                    createJobList();
                    jobListingTable.getItems().addAll(availableJobs);
                    jobListingTable.refresh();

                    //this sets the buttons back to being disabled, until you select a row of the table
                    viewDetailsButton.setDisable(true);
                    acceptJobButton.setDisable(true);
                }
                else
                {
                    // do nothing
                }

            }
            //If selected job is not available, show an error
            else {

                //this displays an error to say that the job isn't available
                alert.setTitle("Job Already Taken");
                alert.setHeaderText("Someone else has already taken this job. Please try another.");
                Optional<ButtonType> result = alert.showAndWait();

                if(!result.isPresent()) {

                    // do nothing

                } else if(result.get() == ButtonType.OK) {
                    //this clears the table, fills the table again
                    jobListingTable.getItems().clear();
                    createJobList();
                    jobListingTable.getItems().addAll(availableJobs);
                    jobListingTable.refresh();

                    //this sets the buttons back to being disabled, until you select a row of the table
                    viewDetailsButton.setDisable(true);
                    acceptJobButton.setDisable(true);

                } else {

                    // do nothing

                }

            }

        }
    }

    //this is the method that changes the scenes for the navigation bar, this is code from Richey Blant's Software
    //Engineering Coursework
    public void changeScene(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent nextScene = null;

        //this checks which button has been pressed and goes to the appropriate page
        if(event.getSource() == helpButton) {
            stage = (Stage) helpButton.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("Help.fxml"));
        } else if(event.getSource() == infoButton){
            stage = (Stage) infoButton.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("FishingInfo.fxml"));
        } else if(event.getSource() == jobListingButton){
            stage = (Stage) jobListingButton.getScene().getWindow();
            nextScene = FXMLLoader.load(getClass().getResource("JobListings.fxml"));
            //this one has to check which type of user is using the system before it can go to the correct page
        } else if(event.getSource() == viewJobsButton){
            stage = (Stage) viewJobsButton.getScene().getWindow();
            //if the user is a fisher
            if(MyFishingPal.currentUser instanceof Fisher)
            {
                //go to the fisher view
                nextScene = FXMLLoader.load(getClass().getResource("FisherView.fxml"));
            }
            //if the user is an intermediary
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
