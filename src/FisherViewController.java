import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.LinkedList;

public class FisherViewController
{
    @FXML
    private TableView<Job> activeJobsTable;
    @FXML
    private TableColumn<Job, Integer> jobIdActiveTbl;
    @FXML
    private TableColumn<Job, String> fishTypeActiveTbl;
    @FXML
    private TableColumn<Job, Integer> amountActiveTbl;
    @FXML
    private TableColumn<Job, Date> dateActiveTbl;
    @FXML
    private TableColumn<Job, Double> payActiveTbl;
    @FXML
    private TableColumn<Intermediary, String> managedActiveTbl;

    @FXML
    private TableView<Job> pastJobsTable;
    @FXML
    private TableColumn<Job, Integer> jobIdPastTbl;
    @FXML
    private TableColumn<Job, String> fishTypePastTbl;
    @FXML
    private TableColumn<Job, Integer> amountPastTbl;
    @FXML
    private TableColumn<Job, Date> datePastTbl;
    @FXML
    private TableColumn<Job, Double> payPastTbl;
    @FXML
    private TableColumn<Intermediary, String> managedPastTbl;

//    Fisher currentUser = (Fisher) MyFishingPal.getUserDetails(LoginController.currentUserId);
//
//    LinkedList<Job> jobs = new LinkedList<>(DatabaseController.selectJobsByFisher(currentUser.getID()));
//    LinkedList<Job> activeJobs = new LinkedList<>();
//    LinkedList<Job> pastJobs = new LinkedList<>();

    public void initialisePage()
    {
//        for(Job job : jobs)
//        {
//            if(!job.isCompleted())
//            {
//                activeJobs.add(job);
//            }
//            else
//            {
//                pastJobs.add(job);
//            }
//        }
//
//        jobIdActiveTbl.setCellValueFactory(new PropertyValueFactory<>("id"));
//        fishTypeActiveTbl.setCellValueFactory(new PropertyValueFactory<>("fishType"));
//        amountActiveTbl.setCellValueFactory(new PropertyValueFactory<>("amountKg"));
//        dateActiveTbl.setCellValueFactory(new PropertyValueFactory<>("dateDue"));
//        payActiveTbl.setCellValueFactory(new PropertyValueFactory<>("payPerKg"));
//        managedActiveTbl.setCellValueFactory(new PropertyValueFactory<>("intermediaryId"));
//
//        activeJobsTable.getItems().addAll(activeJobs);
//
//        jobIdPastTbl.setCellValueFactory(new PropertyValueFactory<>("id"));
//        fishTypePastTbl.setCellValueFactory(new PropertyValueFactory<>("fishType"));
//        amountPastTbl.setCellValueFactory(new PropertyValueFactory<>("amountKg"));
//        datePastTbl.setCellValueFactory(new PropertyValueFactory<>("dateDue"));
//        payPastTbl.setCellValueFactory(new PropertyValueFactory<>("payPerKg"));
//        managedPastTbl.setCellValueFactory(new PropertyValueFactory<>("intermediaryId"));
//
//        pastJobsTable.getItems().addAll(pastJobs);
    }

}
