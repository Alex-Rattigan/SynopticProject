import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyFishingPal extends Application {

    public static Object currentUser;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setWidth(610);
        primaryStage.setHeight(450);
        primaryStage.setTitle("MyFishingPal");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    //Can we delete this method? I don't think we'll ever need it - Alex
    public static Object getUserDetails(int user_id)
    {
        Fisher f = DatabaseController.selectFisherRecord(user_id);

        if(f != null)
        {
            return f;
        }
        else
        {
            Intermediary i = DatabaseController.selectIntermediaryRecord(user_id);
            return i;
        }
    }

    public static void main(String[] args) {

        //Log in to database
        DatabaseController.login();

        //Log in to email
        Email.login();

        //Update local database - in real deployment this would run twice per day, not every startup
        SyncMain.main(new String[]{});

        //Load cached data
        CSVController.loadFiles();

        //Start GUI
        launch(args);

    }

}
