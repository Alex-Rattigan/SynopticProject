/*******************************************************************************************************************
 * File: JobListingsDetailsController.java
 *
 * Date: 9/6/2021
 *
 * Author: Richey Blant
 *
 * Description: This is the page that adds functionality for the JobListingDetails.fxml which shows a job for the user
 * from the job listings page that displays the information of a job that they have selected. If the user is a fisher
 * they can accept the job from this page.
 *
 ******************************************************************************************************************/
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

public class JobListingsDetailsController
{
    //variable definition for the text fields
    @FXML
    private TextField fishTypeText, amountText, payText, dateText, managedByText;
    //variable definition for the text area
    @FXML
    private TextArea detailsText;
    //variable definition for the page buttons
    @FXML
    private Button closeButton, acceptJobButton;

    //creates a job variable
    public static Job currentJob;

    //creates the alert for the page
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    //this method sets the jobs detail from the previous page
    public static void setJobDetails(Job job)
    {
        currentJob = job;
    }

    //this is the method that runs as soon as the page loads
    public void initialize()
    {
        //if the user is an intermediary
        if(MyFishingPal.currentUser instanceof Intermediary){
            //disable to accept job button
            acceptJobButton.setDisable(true);
        }

        //sets the text fields to the information from the job that is selected
        fishTypeText.setText(currentJob.getFishType());
        amountText.setText(String.valueOf(currentJob.getAmountKg()));
        payText.setText(String.valueOf(currentJob.getPayPerKg()));
        dateText.setText(String.valueOf(currentJob.getDateDue()));
        managedByText.setText(currentJob.getIntermediaryName());
        detailsText.setText(currentJob.getDescription());
    }

    //this is the method that accepts a job
    public void acceptJob() throws IOException{
        //checks to make sure the user is a fisher
        if(MyFishingPal.currentUser instanceof Fisher){

            //sets the currently logged in user as the fisher for the job that is selected
            currentJob.setFisherName(((Fisher) MyFishingPal.currentUser).getUsername());

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

            //If job is available, proceed as normal:
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

            }
            //If selected job is not available, show an error
            else {

                alert.setTitle("Job Already Taken");
                alert.setHeaderText("Someone else has already taken this job. Please try another.");
                Optional<ButtonType> result = alert.showAndWait();

            }
        }
    }

    //this is the method that goes back to the previous page if the user clicks the close button
    public void close() throws IOException{
        //changes the scene back to the job listing page
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) closeButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("JobListings.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    //this is the method that changes the scene
    public void changeScene() throws IOException
    {
        //change scene
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) closeButton.getScene().getWindow();

        //checks what kind of user the current user is
        if(MyFishingPal.currentUser instanceof Fisher)
        {
            //if fisher go to the fisher view
            nextScene = FXMLLoader.load(getClass().getResource("FisherView.fxml"));
        }
        else
        {
            //if intermediary go to the intermediary view
            nextScene = FXMLLoader.load(getClass().getResource("IntermediaryView.fxml"));
        }

        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

}
