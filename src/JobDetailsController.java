/*******************************************************************************************************************
 * File: JobDetailsController.java
 *
 * Date: 08/06/2021
 *
 * Author: RM
 *
 * Description: This class is for controlling the JobDetails.fxml page. This controller is used when a user of type
 *              Fisher is viewing the details of a job.
 *
 *              From here, a Fisher can view or abandon a job, and also mark it as complete.
 *
 * References: [1] https://stackoverflow.com/questions/43031602/how-to-set-a-method-to-a-javafx-alert-button
 *             [2] Change scene code from Richey Blant & Ruby Moore's Software Engineering Coursework, provided by
 *             Richey Blant
 *
 ******************************************************************************************************************/


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class JobDetailsController
{
    @FXML
    private TextField fishTypeText, amountText, payText, dateText, managedByText;
    @FXML
    private TextArea detailsText;
    @FXML
    private Button closeButton, jobCompleteButton, abandonJobButton;

    public static Job currentJob;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    // used to pass job from one class to here
    public static void setJobDetails(Job job)
    {
       JobDetailsController.currentJob = job;
    }

    public void initialize()
    {
        fishTypeText.setText(currentJob.getFishType());
        amountText.setText(String.valueOf(currentJob.getAmountKg()));
        payText.setText(String.valueOf(currentJob.getPayPerKg()));
        dateText.setText(String.valueOf(currentJob.getDateDue()));
        managedByText.setText(currentJob.getIntermediaryName());
        detailsText.setText(currentJob.getDescription());

        // disable buttons if job is completed
        if(currentJob.isCompleted())
        {
            abandonJobButton.setDisable(true);
            jobCompleteButton.setDisable(true);
        }
    }

    public void markJobComplete()
    {
        // show confirmation alert
        alert.setTitle("Mark Job as Completed");
        alert.setHeaderText("Are you sure you want to mark this job completed?");
        Optional<ButtonType> result = alert.showAndWait();

        if(!result.isPresent())
        {
            // do nothing
        }
        else if(result.get() == ButtonType.OK)
        {
            CSVController.updateCompleted(currentJob.getId(), true);
            jobCompleteButton.setDisable(true);
            abandonJobButton.setDisable(true);
        }
        else
        {
            // do nothing
        }
    }

    public void abandonJob()
    {
        // show confirmation alert
        alert.setTitle("Abandon Job");
        alert.setHeaderText("Are you sure you want to abandon this job?");
        Optional<ButtonType> result = alert.showAndWait();

        if(!result.isPresent())
        {
            // do nothing
        }
        else if(result.get() == ButtonType.OK)
        {
            CSVController.updateFisherId(currentJob.getId(), null);
            abandonJobButton.setDisable(true);
            jobCompleteButton.setDisable(true);
        }
        else
        {
            // do nothing
        }
    }

    // return to original view
    public void changeScene() throws IOException
    {
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) closeButton.getScene().getWindow();

        if(MyFishingPal.currentUser instanceof Fisher)
        {
            nextScene = FXMLLoader.load(getClass().getResource("FisherView.fxml"));
        }
        else
        {
            nextScene = FXMLLoader.load(getClass().getResource("IntermediaryView.fxml"));
        }

        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

}
