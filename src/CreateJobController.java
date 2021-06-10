import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;


public class CreateJobController
{

    @FXML
    private TextArea jobDetails;

    @FXML
    private TextField priceKg, amountKg, fishType;

    @FXML
    private DatePicker dateToComplete;

    @FXML
    private Button closeButton, createJobButton;

    LocalDate currentDate = LocalDate.now();

    Alert alert = new Alert(Alert.AlertType.INFORMATION);


    public void close() throws IOException
    {
        //changes the scence back to the intermediary view
        Stage stage = null;
        Parent nextScene = null;
        stage = (Stage) closeButton.getScene().getWindow();
        nextScene = FXMLLoader.load(getClass().getResource("IntermediaryView.fxml"));
        assert nextScene != null;
        Scene scene = new Scene(nextScene);
        stage.setScene(scene);
        stage.setTitle("MyFishingPal");
        stage.show();
    }

    public void createJob() throws IOException
    {
        //checks if any of the fields are empty
        if (fishType.getText().equals("") || amountKg.getText().equals("") || priceKg.getText().equals("") || dateToComplete.getValue() == null || jobDetails.getText().equals(""))
        {
            alert.setTitle("Fields empty");
            alert.setHeaderText("Atleast one of the fields are empty, please make sure they are all filled in");
            alert.showAndWait();

        } else {

            if(Integer.parseInt(amountKg.getText()) < 1 || Double.parseDouble(priceKg.getText()) < 0.1)
            {
                alert.setTitle("Incorrect Value");
                alert.setHeaderText("At least one of the fields have an incorrect value, please update the price or amount and try again");
                alert.showAndWait();

            } else {

                if(currentDate.compareTo(dateToComplete.getValue()) < 0 )
                {

                    Intermediary currentUser = (Intermediary) MyFishingPal.currentUser;

                    //adds a job
                    DatabaseController.insertJob(currentUser.getID(), fishType.getText(), Integer.parseInt(amountKg.getText()), Double.parseDouble(priceKg.getText()), Date.valueOf(currentDate), Date.valueOf(dateToComplete.getValue()), jobDetails.getText(), false);

                    //changes scene
                    Stage stage = null;
                    Parent nextScene = null;
                    stage = (Stage) createJobButton.getScene().getWindow();
                    nextScene = FXMLLoader.load(getClass().getResource("IntermediaryView.fxml"));
                    assert nextScene != null;
                    Scene scene = new Scene(nextScene);
                    stage.setScene(scene);
                    stage.setTitle("MyFishingPal");
                    stage.show();

                } else {
                    alert.setTitle("Date Error");
                    alert.setHeaderText("The Date you have entered is before the current date, please try again");
                    alert.showAndWait();
                }

            }
        }

    }

}
