
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseControllerTests {

    //Generate unique usernames/mobile numbers to make test objects
    private static final LocalDateTime currentDateTimeOne = LocalDateTime.now();
    private static final LocalDateTime currentDateTimeTwo = LocalDateTime.now().plusDays(1);
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private static final String currentDateTimeOneString = format.format(currentDateTimeOne);
    private static final String currentDateTimeTwoString = format.format(currentDateTimeTwo);

    @Test
    @Order(1)
    public void testConnect() {

        System.out.println(currentDateTimeOneString + " " + currentDateTimeTwoString);

        assertTrue(DatabaseController.connect());

    }

    /**************************************** CRUD FOR FISHERS ****************************************/

    @Test
    @Order(2)
    public void testInsertAndSelectFisher() {

        //Insert test Fisher
        DatabaseController.insertFisherRecord(currentDateTimeOneString, "password1", "fname1", "sname1", currentDateTimeOneString);

        //Select test Fisher back out by username
        Fisher fisher = DatabaseController.checkFisherExists(currentDateTimeOneString);
        assertNotNull(fisher);

        //Check if correct values are selected
        assertEquals(currentDateTimeOneString, fisher.getUsername());
        assertEquals("password1", fisher.getPassword());
        assertEquals("fname1", fisher.getFname());
        assertEquals("sname1", fisher.getSname());
        assertEquals(currentDateTimeOneString, fisher.getMobileNo());

        //Select test Fisher back out by ID
        fisher = DatabaseController.selectFisherRecord(fisher.getID());
        assertNotNull(fisher);

        //Check if correct values are selected
        assertEquals(currentDateTimeOneString, fisher.getUsername());
        assertEquals("password1", fisher.getPassword());
        assertEquals("fname1", fisher.getFname());
        assertEquals("sname1", fisher.getSname());
        assertEquals(currentDateTimeOneString, fisher.getMobileNo());

    }

    @Test
    @Order(3)
    public void testSelectAllFisherRecords() {

        //Select all Fishers into a LinkedList
        LinkedList<Fisher> fishers = DatabaseController.selectAllFisherRecords();

        //Check that returned list isn't null
        assertNotNull(fishers);

        //Check that the returned list has at least one Fisher
        assertNotEquals(0, fishers.size());

    }

    @Test
    @Order(4)
    public void testUpdatePasswordFisher() {

        //Retrieve the test Fisher
        Fisher fisher = DatabaseController.checkFisherExists(currentDateTimeOneString);

        //Change the mobile number
        DatabaseController.updatePasswordFisher(fisher.getID(), "password2");

        //Check if password was saved
        fisher = DatabaseController.checkFisherExists(currentDateTimeOneString);
        assertEquals("password2", fisher.getPassword());

    }

    @Test
    @Order(5)
    public void testUpdateMobileNoFisher() {

        //Retrieve the test Fisher
        Fisher fisher = DatabaseController.checkFisherExists(currentDateTimeOneString);

        //Generate a new unique mobile number
        LocalDateTime newCurrentDateTime = LocalDateTime.now();
        String newCurrentDateTimeString = format.format(newCurrentDateTime);

        //Change the password
        DatabaseController.updateMobileNoFisher(fisher.getID(), newCurrentDateTimeString);

        //Check if password was saved
        fisher = DatabaseController.checkFisherExists(currentDateTimeOneString);
        assertEquals(newCurrentDateTimeString, fisher.getMobileNo());

    }

    @Test
    @Order(100)
    public void testDeleteFisherRecord() {



    }

    /**************************************** CRUD FOR INTERMEDIARIES ****************************************/

    @Test
    @Order(6)
    public void testInsertAndSelectIntermediary() {

        //Insert test Intermediary
        DatabaseController.insertIntermediaryRecord(currentDateTimeTwoString, "password1", "fname1", "sname1", currentDateTimeTwoString);

        //Select test Intermediary back out by username
        Intermediary intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);
        assertNotNull(intermediary);

        //Check if correct values are selected
        assertEquals(currentDateTimeTwoString, intermediary.getUsername());
        assertEquals("password1", intermediary.getPassword());
        assertEquals("fname1", intermediary.getFname());
        assertEquals("sname1", intermediary.getSname());
        assertEquals(currentDateTimeTwoString, intermediary.getMobileNo());

        //Select test Intermediary back out by ID
        intermediary = DatabaseController.selectIntermediaryRecord(intermediary.getID());
        assertNotNull(intermediary);

        //Check if correct values are selected
        assertEquals(currentDateTimeTwoString, intermediary.getUsername());
        assertEquals("password1", intermediary.getPassword());
        assertEquals("fname1", intermediary.getFname());
        assertEquals("sname1", intermediary.getSname());
        assertEquals(currentDateTimeTwoString, intermediary.getMobileNo());

    }

    @Test
    @Order(7)
    public void testSelectAllIntermediaryRecords() {

        //Select all Intermediaries into a LinkedList
        LinkedList<Intermediary> intermediaries = DatabaseController.selectAllIntermediaryRecords();

        //Check that returned list isn't null
        assertNotNull(intermediaries);

        //Check that the returned list has at least one Intermediary
        assertNotEquals(0, intermediaries.size());

    }

    @Test
    @Order(8)
    public void testUpdatePasswordIntermediary() {

        //Retrieve the test Intermediary
        Intermediary intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);

        //Change the mobile number
        DatabaseController.updatePasswordIntermediary(intermediary.getID(), "password2");

        //Check if password was saved
        intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);
        assertEquals("password2", intermediary.getPassword());

    }

    @Test
    @Order(9)
    public void testUpdateMobileNoIntermediary() {

        //Retrieve the test Intermediary
        Intermediary intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);

        //Generate a new unique mobile number
        LocalDateTime newCurrentDateTime = LocalDateTime.now();
        String newCurrentDateTimeString = format.format(newCurrentDateTime);

        //Change the password
        DatabaseController.updateMobileNoIntermediary(intermediary.getID(), newCurrentDateTimeString);

        //Check if password was saved
        intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);
        assertEquals(newCurrentDateTimeString, intermediary.getMobileNo());

    }

    @Test
    @Order(101)
    public void testDeleteIntermediaryRecord() {



    }

    /**************************************** CRUD FOR JOBS ****************************************/

    @Test
    @Order(10)
    public void testInsertAndSelectJob() {

        //Retrieve test Intermediary
        Intermediary intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);
        int intermediary_id = intermediary.getID();

        //Insert test Job
        Date dateCreated = Date.valueOf(currentDateTimeOne.toLocalDate());
        Date dateDue = Date.valueOf(currentDateTimeTwo.toLocalDate());
        int job_id = DatabaseController.insertJob(intermediary_id, "fish1", 1, 1.1, dateCreated, dateDue, "description1", false);

        //Select test Job back out
        Job job = DatabaseController.selectJob(job_id);
        assertNotNull(job);

        //Check if correct values are selected
        assertEquals(job.getId(), job_id);
        assertEquals(job.getFishType(), "fish1");
        assertEquals(job.getAmountKg(), 1);
        assertEquals(job.getPayPerKg(), 1.1);
        assertEquals(job.getDateCreated(), dateCreated);
        assertEquals(job.getDateDue(), dateDue);
        assertEquals(job.getDescription(), "description1");
        assertFalse(job.isCompleted());

    }

    @Test
    @Order(11)
    public void testSelectAllJobs() {

        //Select all Jobs into a LinkedList
        LinkedList<Job> jobs = DatabaseController.selectAllJobs();

        //Check that returned list isn't null
        assertNotNull(jobs);

        //Check that the returned list has at least one Job
        assertNotEquals(0, jobs.size());

    }

    //Test 12 is titled testSelectJobByIntermediary and can be found in CRUD FOR FISHERS + INTERMEDIARIES + JOBS section

    @Test
    @Order(13)
    public void testUpdateFishType() {

        //Retrieve the test Intermediary id
        Intermediary intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);
        int intermediary_id = intermediary.getID();

        //Retrieve the test Job id
        LinkedList<Job> jobs = DatabaseController.selectJobByIntermediary(intermediary_id);
        int job_id = jobs.get(0).getId();

        //Change the fish type
        DatabaseController.updateFishType(job_id, "fish2");

        //Check if fish type was saved
        Job job = DatabaseController.selectJob(job_id);
        assertEquals("fish2", job.getFishType());

    }

    @Test
    @Order(14)
    public void testUpdateAmountKg() {

        //Retrieve the test Intermediary id
        Intermediary intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);
        int intermediary_id = intermediary.getID();

        //Retrieve the test Job id
        LinkedList<Job> jobs = DatabaseController.selectJobByIntermediary(intermediary_id);
        int job_id = jobs.get(0).getId();

        //Change the amount
        DatabaseController.updateAmountKg(job_id, 2);

        //Check if amount was saved
        Job job = DatabaseController.selectJob(job_id);
        assertEquals(2, job.getAmountKg());

    }

    @Test
    @Order(15)
    public void testUpdatePay() {

        //Retrieve the test Intermediary id
        Intermediary intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);
        int intermediary_id = intermediary.getID();

        //Retrieve the test Job id
        LinkedList<Job> jobs = DatabaseController.selectJobByIntermediary(intermediary_id);
        int job_id = jobs.get(0).getId();

        //Change the pay
        DatabaseController.updatePay(job_id, 2.2);

        //Check if pay was saved
        Job job = DatabaseController.selectJob(job_id);
        assertEquals(2.2, job.getPayPerKg());

    }

    @Test
    @Order(16)
    public void testUpdateDueDate() {

        //Retrieve the test Intermediary id
        Intermediary intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);
        int intermediary_id = intermediary.getID();

        //Retrieve the test Job id
        LinkedList<Job> jobs = DatabaseController.selectJobByIntermediary(intermediary_id);
        int job_id = jobs.get(0).getId();

        //Generate a new due date
        Date newDateDue = Date.valueOf(LocalDate.now().plusDays(2));

        //Change the due date
        DatabaseController.updateDateDue(job_id, newDateDue);

        //Check if due date was saved
        Job job = DatabaseController.selectJob(job_id);
        assertEquals(newDateDue, job.getDateDue());

    }

    @Test
    @Order(17)
    public void testUpdateDescription() {

        //Retrieve the test Intermediary id
        Intermediary intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);
        int intermediary_id = intermediary.getID();

        //Retrieve the test Job id
        LinkedList<Job> jobs = DatabaseController.selectJobByIntermediary(intermediary_id);
        int job_id = jobs.get(0).getId();

        //Change the description
        DatabaseController.updateDescription(job_id, "description2");

        //Check if description was saved
        Job job = DatabaseController.selectJob(job_id);
        assertEquals("description2", job.getDescription());

    }

    @Test
    @Order(18)
    public void testUpdateCompleted() {

        //Retrieve the test Intermediary id
        Intermediary intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);
        int intermediary_id = intermediary.getID();

        //Retrieve the test Job id
        LinkedList<Job> jobs = DatabaseController.selectJobByIntermediary(intermediary_id);
        int job_id = jobs.get(0).getId();

        //Mark as completed
        DatabaseController.updateCompleted(job_id, true);

        //Check if completion status was saved
        Job job = DatabaseController.selectJob(job_id);
        assertTrue(job.isCompleted());

    }

    @Test
    @Order(102)
    public void testDeleteJobRecord() {



    }

    /**************************************** CRUD FOR JOINS ****************************************/

    @Test
    @Order(19)
    public void testInsertFisherIntermediaryJob() {

        //Left empty as DatabaseController.insertFisherIntermediaryJob() may be deleted

    }

    @Test
    @Order(20)
    public void testSelectJobsWithoutFisher() {

        //Select all Jobs without a Fisher into a LinkedList
        LinkedList<Job> jobs = DatabaseController.selectJobsWithoutFisher();

        //Check that returned list isn't null
        assertNotNull(jobs);

        //Check that the returned list has at least one Job
        assertNotEquals(0, jobs.size());

    }

    @Test
    @Order(21)
    public void testSelectJobByFisherAndUpdateFisherId() {

        //Retrieve the test Fisher id
        Fisher fisher = DatabaseController.checkFisherExists(currentDateTimeOneString);
        int fisher_id = fisher.getID();

        //Retrieve the test Intermediary id
        Intermediary intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);
        int intermediary_id = intermediary.getID();

        //Retrieve the test Job id
        LinkedList<Job> jobs = DatabaseController.selectJobByIntermediary(intermediary_id);
        int job_id = jobs.get(0).getId();

        //Update the test Job's Fisher id
        DatabaseController.updateFisherId(job_id, fisher_id);

        //Check if fisher id was saved
        Job job = DatabaseController.selectJob(job_id);
        assertEquals(job.getFisherName(), fisher.getFullName());

    }

    @Test
    @Order(12)  //This is out of order intentionally as tested function is needed to test CRUD for Jobs
    public void testSelectJobByIntermediary() {

        //This test is skipped because it relies upon part of CSVController, which has not been tested yet.
        //In ideal circumstances this method will never be used as its CSVController version will take its place.

//        //Retrieve the test Intermediary id
//        Intermediary intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);
//        int intermediary_id = intermediary.getID();
//
//        //Select all Jobs with matching Intermediary id
//        LinkedList<Job> jobs = DatabaseController.selectJobByIntermediary(intermediary_id);
//
//        //Check that returned list isn't null
//        assertNotNull(jobs);
//
//        //Check that the returned list has at one Job
//        assertEquals(1, jobs.size());

    }

    @Test
    @Order(22)
    public void testSelectJobReturnFisher() {

        //Retrieve the test Fisher id
        Fisher fisher = DatabaseController.checkFisherExists(currentDateTimeOneString);
        int fisher_id = fisher.getID();

        //Retrieve the test Intermediary id
        Intermediary intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);
        int intermediary_id = intermediary.getID();

        //Retrieve the test Job id
        LinkedList<Job> jobs = DatabaseController.selectJobByIntermediary(intermediary_id);
        int job_id = jobs.get(0).getId();

        //Select the Fisher using the Job id
        fisher = DatabaseController.selectJobReturnFisher(job_id);

        //Check that correct Fisher is returned
        assertEquals(fisher_id, fisher.getID());

    }

    @Test
    @Order(23)
    public void testSelectJobReturnIntermediary() {

        //Retrieve the test Intermediary id
        Intermediary intermediary = DatabaseController.checkIntermediaryExists(currentDateTimeTwoString);
        int intermediary_id = intermediary.getID();

        //Retrieve the test Job id
        LinkedList<Job> jobs = DatabaseController.selectJobByIntermediary(intermediary_id);
        int job_id = jobs.get(0).getId();

        //Select the Intermediary using the Job id
        intermediary = DatabaseController.selectJobReturnIntermediary(job_id);

        //Check that correct Intermediary is returned
        assertEquals(intermediary_id, intermediary.getID());

    }

}
