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
    @FXML
    private TextField fishTypeText, amountText, payText, dateText, managedByText;
    @FXML
    private TextArea detailsText;
    @FXML
    private Button closeButton, acceptJobButton;

    public static Job currentJob;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    public static void setJobDetails(Job job)
    {
        currentJob = job;
    }

    public void initialize()
    {
        if(MyFishingPal.currentUser instanceof Intermediary){
            acceptJobButton.setDisable(true);
        }

        fishTypeText.setText(currentJob.getFishType());
        amountText.setText(String.valueOf(currentJob.getAmountKg()));
        payText.setText(String.valueOf(currentJob.getPayPerKg()));
        dateText.setText(String.valueOf(currentJob.getDateDue()));
        managedByText.setText(currentJob.getIntermediaryName());
        detailsText.setText(currentJob.getDescription());
    }

    public void acceptJob() throws IOException{
        if(MyFishingPal.currentUser instanceof Fisher){

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

                currentJob.setFisherName(((Fisher) MyFishingPal.currentUser).getUsername());

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

    public void close() throws IOException{
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
