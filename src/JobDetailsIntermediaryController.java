import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Optional;

// References: http://tutorials.jenkov.com/java-date-time/parsing-formatting-dates.html

public class JobDetailsIntermediaryController
{
    @FXML
    private TextField fishTypeText, amountText, payText, dateText, acceptedByText;
    @FXML
    private TextArea detailsText;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button closeButton, modifyButton, removeJobButton;

    private static Job currentJob;
    private static boolean isFromButton;

    public static void setIsFromButton(boolean b)
    {
        isFromButton = b;
    }

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    Alert savedAlert = new Alert(Alert.AlertType.INFORMATION);

    public static void setJobDetails(Job job)
    {
        currentJob = job;
    }

    public void initialize()
    {
        fishTypeText.setText(currentJob.getFishType());
        amountText.setText(String.valueOf(currentJob.getAmountKg()));
        payText.setText(String.valueOf(currentJob.getPayPerKg()));

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = format.format(currentJob.getDateDue());
        dateText.setText(dateString);
        datePicker.setVisible(false);
        datePicker.setValue(currentJob.getDateDue().toLocalDate());

        LinkedList<Job> jobsWithoutFishers = CSVController.selectJobsWithoutFisher();

        boolean isNullFisher = false;

        for(Job job : jobsWithoutFishers)
        {
            if(job.getId() == currentJob.getId())
            {
                isNullFisher = true;
            }
        }

        if(isNullFisher)
        {
            acceptedByText.setText("-");
        }
        else
        {
            acceptedByText.setText(CSVController.selectJobReturnFisher(currentJob.getId()).getFname() + " " +
                    CSVController.selectJobReturnFisher(currentJob.getId()).getSname());
        }

        detailsText.setText(currentJob.getDescription());


        if(currentJob.isCompleted() || currentJob.getFisherName() != null)
        {
            modifyButton.setDisable(true);
            removeJobButton.setDisable(true);
        }

        if(isFromButton)
        {
            modifyDetails();
        }
    }

    public void modifyDetails()
    {
        fishTypeText.setEditable(true);
        amountText.setEditable(true);
        payText.setEditable(true);

        dateText.setVisible(false);
        datePicker.setVisible(true);

        detailsText.setEditable(true);

        boolean doWrite = true;

        if(modifyButton.getText().equals("Save Changes"))
        {
            if(fishTypeText.getText().equals("") && doWrite)
            {
                doWrite = false;
                savedAlert.setTitle("Input Error");
                savedAlert.setHeaderText("Invalid entry in field 'Fish type'.");
                savedAlert.showAndWait();
            }

            if((amountText.getText().equals("") || Integer.parseInt(amountText.getText()) <= 0) && doWrite)
            {
                doWrite = false;
                savedAlert.setTitle("Input Error");
                savedAlert.setHeaderText("Invalid entry in field 'Amount'.");
                savedAlert.showAndWait();
            }

            if((payText.getText().equals("") || Double.parseDouble(payText.getText()) <= 0) && doWrite)
            {
                doWrite = false;
                savedAlert.setTitle("Input Error");
                savedAlert.setHeaderText("Invalid entry in field 'Pay'.");
                savedAlert.showAndWait();
            }

            if((datePicker.getValue().isBefore(LocalDate.now()) && doWrite) || (datePicker.getValue().isEqual(LocalDate.now()) && doWrite))
            {
                doWrite = false;
                savedAlert.setTitle("Input Error");
                savedAlert.setHeaderText("Invalid entry in field 'Date Due'.");
                savedAlert.showAndWait();
            }

            if(doWrite)
            {
                if(!fishTypeText.getText().equals(currentJob.getFishType()))
                {
                    CSVController.updateFishType(currentJob.getId(), fishTypeText.getText());
                }

                if(Integer.parseInt(amountText.getText()) != currentJob.getAmountKg())
                {
                    CSVController.updateAmountKg(currentJob.getId(), Integer.parseInt(amountText.getText()));
                }

                if(Double.parseDouble(payText.getText()) != currentJob.getPayPerKg())
                {
                    CSVController.updatePay(currentJob.getId(), Double.parseDouble(payText.getText()));
                }

                if(!datePicker.getValue().isEqual(currentJob.getDateDue().toLocalDate()))
                {
                    CSVController.updateDateDue(currentJob.getId(), Date.valueOf(datePicker.getValue()));
                }

                if(!detailsText.getText().equals(currentJob.getDescription()))
                {
                    CSVController.updateDescription(currentJob.getId(), detailsText.getText());
                }
            }
        }

        if(modifyButton.getText().equals("Save Changes") && doWrite)
        {
            savedAlert.setTitle("Changes Saved");
            savedAlert.setHeaderText("Your changes have been saved.");
            savedAlert.showAndWait();
        }

        modifyButton.setText("Save Changes");
    }

    public void removeJob()
    {
        alert.setTitle("Remove Job");
        alert.setHeaderText("Are you sure you want to remove this job?");
        Optional<ButtonType> result = alert.showAndWait();

        if(!result.isPresent())
        {
            // do nothing
        }
        else if(result.get() == ButtonType.OK)
        {
            CSVController.deleteJob(currentJob.getId());
            removeJobButton.setDisable(true);
            modifyButton.setDisable(true);
        }
        else
        {
            // do nothing
        }
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
