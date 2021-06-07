import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import java.io.IOException;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class LoginController
{
    @FXML
    private PasswordField passwordtxt;

    @FXML
    private TextField usernametxt;

    @FXML
    private Label labeltxt;

    @FXML
    private Button loginbtn;

    public void Login(javafx.event.ActionEvent actionEvent) throws IOException
    {
        checkPassword();
    }

    public void checkPassword() throws IOException
    {
        //test data
        //DatabaseController.insertFisherRecord("james9201","pass","james","rod","07308247748");
        //DatabaseController.insertIntermediaryRecord("frank92041","test","james","rod","07309247748");

        //checks if the username matches any current usernames
        Intermediary currentIntermediary = DatabaseController.checkIntermediaryExists(usernametxt.getText());
        Fisher currentFisher = DatabaseController.checkFisherExists(usernametxt.getText());


        //if the username doesnt exist
        if (currentFisher == null && currentIntermediary == null)
        {
            labeltxt.setText("Enter a valid username");
        }

        //login for intermediary
        if (currentFisher == null)
        {
            if (usernametxt.getText().equals(currentIntermediary.getUsername()))
            {
                if (passwordtxt.getText().equals(currentIntermediary.getPassword()))
                {
                    labeltxt.setText("Success");

                    //load the new scene
                    Stage stage;
                    Parent nextScene;

                    stage = (Stage) loginbtn.getScene().getWindow();
                    nextScene = FXMLLoader.load(getClass().getResource("FishingInfo.fxml"));

                    assert nextScene != null;
                    Scene scene = new Scene(nextScene);
                    stage.setScene(scene);
                    stage.setTitle("MyFishingPal");
                    stage.show();

                } else {

                    labeltxt.setText("Password Incorrect");
                }

            } else {

                labeltxt.setText("Username Incorrect");
            }

            //login for fisherman
        } else {

            if (usernametxt.getText().equals(currentFisher.getUsername()))
            {
                if (passwordtxt.getText().equals(currentFisher.getPassword()))
                {
                    labeltxt.setText("Success");

                    //load the new scene
                    Stage stage;
                    Parent nextScene;

                    stage = (Stage) loginbtn.getScene().getWindow();
                    nextScene = FXMLLoader.load(getClass().getResource("FishingInfo.fxml"));

                    assert nextScene != null;
                    Scene scene = new Scene(nextScene);
                    stage.setScene(scene);
                    stage.setTitle("MyFishingPal");
                    stage.show();

                } else {

                    labeltxt.setText("Password Incorrect");
                }

            } else {

                labeltxt.setText("Username Incorrect");
            }

        }

    }

}
