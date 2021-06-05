import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;



public class SignUpController {

    @FXML
    private TextField forenameTxt, surnameTxt, usernameTxt, passwordTxt, mobileNoTxt;

    @FXML
    private Button completeSignUpButton;

    @FXML
    private RadioButton intermediaryButton, fisherButton;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void completeSignUp() throws Exception {
        try {
            String forename = forenameTxt.getText();
            String username = usernameTxt.getText();
            String surname = surnameTxt.getText();
            String password = passwordTxt.getText();
            String mobileNo = mobileNoTxt.getText();

            if (intermediaryButton.isSelected()) {
                if(checkDetails()) {
//                DatabaseController.insertIntermediaryRecord(forename, surname, username, password, mobileNo);

                    Stage stage;
                    Parent myNewScene;

                    stage = (Stage) completeSignUpButton.getScene().getWindow();
                    myNewScene = FXMLLoader.load(getClass().getResource("Login.fxml"));

                    Scene scene = new Scene(myNewScene);
                    stage.setWidth(710);
                    stage.setHeight(550);
                    stage.setScene(scene);
                    stage.setTitle("MyFishingPal");
                    stage.show();
                }
                else{
                    alert.setTitle("Username or phone number may be invalid");
                    alert.setHeaderText("An account already exists with either that username or phone number. Please try again");
                    alert.showAndWait();
                }

            } else if (fisherButton.isSelected())
            {
                if(checkDetails()) {
//                DatabaseController.insertFisherRecord(forename, surname, username, password, mobileNo);

                    Stage stage;
                    Parent myNewScene;

                    stage = (Stage) completeSignUpButton.getScene().getWindow();
                    myNewScene = FXMLLoader.load(getClass().getResource("Login.fxml"));

                    Scene scene = new Scene(myNewScene);
                    stage.setWidth(710);
                    stage.setHeight(550);
                    stage.setScene(scene);
                    stage.setTitle("MyFishingPal");
                    stage.show();
                }
                else{
                    alert.setTitle("Username or phone number may be invalid");
                    alert.setHeaderText("An account already exists with either that username or phone number. Please try again");
                    alert.showAndWait();
                }

            } else{
                alert.setTitle("Please check either Intermediary or Fisher");
                alert.setHeaderText("Please select an option");
                alert.showAndWait();
            }



        }catch (Exception e){
            alert.setTitle("Please enter the correct information");
            alert.setHeaderText("Invalid information in one or more fields");
            alert.showAndWait();
        }
    }

    public boolean checkDetails(){
        String email = mobileNoTxt.getText();

        String emailTest = "";

        String forename = forenameTxt.getText();
        String surname = surnameTxt.getText();
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        if(email.contains(Character.toString('@')) && email.contains(Character.toString('.')) && forename !=null
                && surname != null && username !=null && password!=null)
        {
            System.out.println("Email is valid");
            emailTest = email;
            return true;
        }
        else {
            alert.setTitle("Incorrect email format");
            alert.setHeaderText("The email address entered is not valid");
            alert.showAndWait();
            System.out.println("invalid email");
        }
        return false;
    }
}
