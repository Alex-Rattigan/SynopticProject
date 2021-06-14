
/****************************************
 *
 * File:        CSVController.java
 *
 * Date:        06/06/2021
 *
 * Author:      Alex Rattigan
 *
 * Description: Provides a copy of every method in DatabaseController.java, as well as some file management methods,
 *              to allow the program to run off a local database cache rather than accessing the internet every time
 *              data is needed.
 *
 ***************************************/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;

public class CSVController {

    //Cache files
    private static final String fishersFile = "Fishers.mfp";
    private static final String intermediariesFile = "Intermediaries.mfp";
    private static final String jobsFile = "Jobs.mfp";
    private static final String joinsFile = "Joins.mfp";

    //ArrayLists to hold data in memory for faster access
    private static final ArrayList<Fisher> fishers = new ArrayList<>();
    private static final ArrayList<Intermediary> intermediaries = new ArrayList<>();
    private static final ArrayList<Job> jobs = new ArrayList<>();
    private static final ArrayList<String[]> joins = new ArrayList<>();

    //Load data from Cache files to memory
    public static void loadFiles() {

        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(fishersFile));
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                String[] split = line.split(",");
                fishers.add(new Fisher(Integer.parseInt(split[0]), split[1], split[2], split[3], split[4], split[5]));

            }
            bufferedReader.close();

            bufferedReader = new BufferedReader(new FileReader(intermediariesFile));
            while ((line = bufferedReader.readLine()) != null) {

                String[] split = line.split(",");
                intermediaries.add(new Intermediary(Integer.parseInt(split[0]), split[1], split[2], split[3], split[4], split[5]));

            }
            bufferedReader.close();

            bufferedReader = new BufferedReader(new FileReader(jobsFile));
            while ((line = bufferedReader.readLine()) != null) {

                String[] split = line.split(",");

                jobs.add(new Job(Integer.parseInt(split[0]), split[1], Integer.parseInt(split[2]), Double.parseDouble(split[3]), Date.valueOf(split[4]), Date.valueOf(split[5]), split[6], Boolean.parseBoolean(split[7])));

            }
            bufferedReader.close();

            bufferedReader = new BufferedReader(new FileReader(joinsFile));
            while ((line = bufferedReader.readLine()) != null) {

                joins.add(line.split(","));

            }
            bufferedReader.close();

        } catch (Exception e) {

            System.out.println(fishersFile + ", " + intermediariesFile + ", " + jobsFile + ", or " + joinsFile + " failed to read.");
            System.exit(1);

        }

    }

    //Write data from memory to cache files
    private static void writeFiles() {

        try {

            FileWriter fileWriter = new FileWriter(fishersFile);
            for (Fisher fisher : fishers) {

                fileWriter.write(fisher.getID() + "," + fisher.getUsername() + "," + fisher.getPassword() + "," + fisher.getFname() + "," + fisher.getSname() + "," + fisher.getMobileNo() + "\n");

            }
            fileWriter.flush();
            fileWriter.close();

            fileWriter = new FileWriter(intermediariesFile);
            for (Intermediary intermediary : intermediaries) {

                fileWriter.write(intermediary.getID() + "," + intermediary.getUsername() + "," + intermediary.getPassword() + "," + intermediary.getFname() + "," + intermediary.getSname() + "," + intermediary.getMobileNo() + "\n");

            }
            fileWriter.flush();
            fileWriter.close();

            fileWriter = new FileWriter(jobsFile);
            for (Job job : jobs) {

                fileWriter.write(job.getId() + "," + job.getFishType() + "," + job.getAmountKg() + "," + job.getPayPerKg() + "," + job.getDateCreated() + "," + job.getDateDue() + "," + job.getDescription() + "," + job.isCompleted() + "\n");

            }
            fileWriter.flush();
            fileWriter.close();

            fileWriter = new FileWriter(joinsFile);
            for (String[] join : joins) {

                fileWriter.write(join[0] + "," + join[1] + "," + join[2] + "\n");

            }
            fileWriter.flush();
            fileWriter.close();

        } catch (Exception e) {

            System.out.println(fishersFile + ", " + intermediariesFile + ", " + jobsFile + ", or " + joinsFile + " failed to write.");

        }

    }

    /**************************************** CRUD FOR FISHERS ****************************************/

    public static int insertFisherRecord(String username, String password, String fname, String lname, String mobNo) {

        DatabaseController.insertFisherRecord(username, password, fname, lname, mobNo);

        Fisher fisher = DatabaseController.checkFisherExists(username);

        assert fisher != null;
        int id = fisher.getID();

        fishers.add(new Fisher(id, username, password, fname, lname, mobNo));

        writeFiles();

        return id;

    }

    public static Fisher checkFisherExists(String username) {

        for (Fisher fisher : fishers) {

            if (fisher.getUsername().equals(username)) {

                return fisher;

            }

        }

        return null;

    }

    public static LinkedList<Fisher> selectAllFisherRecords() {

        return new LinkedList<>(fishers);

    }

    public static Fisher selectFisherRecord(int fisher_id) {

        for (Fisher fisher : fishers) {

            if (fisher.getID() == fisher_id) {

                return fisher;

            }

        }

        return null;

    }

    public static void updatePasswordFisher(int fisher_id, String new_password) {

        DatabaseController.updatePasswordFisher(fisher_id, new_password);

        for (Fisher fisher : fishers) {

            if (fisher.getID() == fisher_id) {

                fisher.setPassword(new_password);

            }

        }

        writeFiles();

    }

    public static void updateMobileNoFisher(int fisher_id, String new_mob_no) {

        DatabaseController.updateMobileNoFisher(fisher_id, new_mob_no);

        for (Fisher fisher : fishers) {

            if (fisher.getID() == fisher_id) {

                fisher.setMobileNo(new_mob_no);

            }

        }

        writeFiles();

    }

    public static void deleteFisherRecord(int fisher_id) {

        DatabaseController.deleteFisherRecord(fisher_id);

        int index = -1;

        for (int i = 0; i < fishers.size(); i ++) {

            if (fishers.get(i).getID() == fisher_id) {

                index = i;

            }

        }

        if(index != -1) {

            fishers.remove(index);

            writeFiles();

        }

    }

    /**************************************** CRUD FOR INTERMEDIARIES ****************************************/

    public static int insertIntermediaryRecord(String username, String password, String fname, String lname, String mobNo) {

        DatabaseController.insertIntermediaryRecord(username, password, fname, lname, mobNo);

        Intermediary intermediary = DatabaseController.checkIntermediaryExists(username);

        assert intermediary != null;
        int id = intermediary.getID();

        intermediaries.add(new Intermediary(id, username, password, fname, lname, mobNo));

        writeFiles();

        return id;

    }

    public static Intermediary checkIntermediaryExists(String username) {

        for (Intermediary intermediary : intermediaries) {

            if (intermediary.getUsername().equals(username)) {

                return intermediary;

            }

        }

        return null;

    }

    public static LinkedList<Intermediary> selectAllIntermediaryRecords() {

        return new LinkedList<>(intermediaries);

    }

    public static Intermediary selectIntermediaryRecord(int intermediary_id) {

        for (Intermediary intermediary : intermediaries) {

            if (intermediary.getID() == intermediary_id) {

                return intermediary;

            }

        }

        return null;

    }

    public static void updatePasswordIntermediary(int intermediary_id, String new_password) {

        DatabaseController.updatePasswordIntermediary(intermediary_id, new_password);

        for (Intermediary intermediary : intermediaries) {

            if (intermediary.getID() == intermediary_id) {

                intermediary.setPassword(new_password);

            }

        }

        writeFiles();

    }

    public static void updateMobileNoIntermediary(int intermediary_id, String new_mob_no) {

        DatabaseController.updateMobileNoIntermediary(intermediary_id, new_mob_no);

        for (Intermediary intermediary : intermediaries) {

            if (intermediary.getID() == intermediary_id) {

                intermediary.setMobileNo(new_mob_no);

            }

        }

        writeFiles();

    }

    public static void deleteIntermediaryRecord(int intermediary_id) {

        DatabaseController.deleteIntermediaryRecord(intermediary_id);

        int index = -1;

        for (int i = 0; i < intermediaries.size(); i ++) {

            if (intermediaries.get(i).getID() == intermediary_id) {

                index = i;

            }

        }

        if (index != -1) {

            intermediaries.remove(index);

            writeFiles();

        }

    }

    /**************************************** CRUD FOR JOBS ****************************************/

    public static int insertJob(int intermediary_id, String fish_type, int amount_kg, double pay_per_kg,
                                      Date date_created, Date date_due, String description, boolean is_completed) {

        int id = DatabaseController.insertJob(intermediary_id, fish_type, amount_kg, pay_per_kg, date_created, date_due,
                description, is_completed);

        jobs.add(new Job(id, fish_type, amount_kg, pay_per_kg, date_created, date_due, description, is_completed));

        joins.add(new String[]{String.valueOf(id), String.valueOf(intermediary_id), "0"});

        writeFiles();

        return id;

    }

    public static LinkedList<Job> selectAllJobs() {

        return new LinkedList<>(jobs);

    }

    public static Job selectJob(int job_id) {

        for (Job job : jobs) {

            if (job.getId() == job_id) {

                return job;

            }

        }

        return null;

    }

    public static void updateFishType(int job_id, String new_type) {

        DatabaseController.updateFishType(job_id, new_type);

        for (Job job : jobs) {

            if (job.getId() == job_id) {

                job.setFishType(new_type);

            }

        }

        writeFiles();

    }

    public static void updateAmountKg(int job_id, int new_amount) {

        DatabaseController.updateAmountKg(job_id, new_amount);

        for (Job job : jobs) {

            if (job.getId() == job_id) {

                job.setAmountKg(new_amount);

            }

        }

        writeFiles();

    }

    public static void updatePay(int job_id, double new_pay){

        DatabaseController.updatePay(job_id, new_pay);

        for (Job job : jobs) {

            if (job.getId() == job_id) {

                job.setPayPerKg(new_pay);

            }

        }

        writeFiles();

    }

    public static void updateDateDue(int job_id, Date new_date) {

        DatabaseController.updateDateDue(job_id, new_date);

        for (Job job : jobs) {

            if (job.getId() == job_id) {

                job.setDateDue(new_date);

            }

        }

        writeFiles();

    }

    public static void updateDescription(int job_id, String new_description) {

        DatabaseController.updateDescription(job_id, new_description);

        for (Job job : jobs) {

            if (job.getId() == job_id) {

                job.setDescription(new_description);

            }

        }

        writeFiles();

    }

    public static void updateCompleted(int job_id, boolean is_completed) {

        DatabaseController.updateCompleted(job_id, is_completed);

        for (Job job : jobs) {

            if (job.getId() == job_id) {

                job.setCompleted(is_completed);

            }

        }

        writeFiles();

    }

    public static void deleteJob(int job_id) {

        DatabaseController.deleteJob(job_id);

        int index = -1;

        for (int i = 0; i < jobs.size(); i ++) {

            if (jobs.get(i).getId() == job_id) {

                index = i;

            }

        }

        jobs.remove(index);

        index = -1;

        for (int i = 0; i < joins.size(); i ++) {

            if (Integer.parseInt(joins.get(i)[0]) == job_id) {

                index = i;

            }

        }

        if (index != -1) {

            joins.remove(index);

            writeFiles();

        }

    }

    /**************************************** CRUD FOR FISHERS + INTERMEDIARIES + JOBS ****************************************/

    public static void insertFisherIntermediaryJob(int job_id, int intermediary_id, Integer fisher_id_integer) {

        int fisher_id = 0;

        if (fisher_id_integer != null) {

            fisher_id = fisher_id_integer;

        }

        DatabaseController.insertFisherIntermediaryJob(job_id, intermediary_id, fisher_id);

        joins.add(new String[]{String.valueOf(job_id), String.valueOf(intermediary_id), String.valueOf(fisher_id)});

        writeFiles();

    }

    public static LinkedList<Job> selectJobsWithoutFisher() {

        LinkedList<Integer> job_ids = new LinkedList<>();
        LinkedList<Integer> intermediary_ids = new LinkedList<>();

        for (String[] join : joins) {

            if (Integer.parseInt(join[2]) == 0) {

                job_ids.add(Integer.parseInt(join[0]));
                intermediary_ids.add(Integer.parseInt(join[1]));

            }

        }

        LinkedList<Job> jobsWithoutFisher = new LinkedList<Job>();

        for (int i = 0; i < job_ids.size(); i++) {

            jobsWithoutFisher.add(new Job(selectJob(job_ids.get(i)), intermediary_ids.get(i)));

        }

        return jobsWithoutFisher;

    }

    public static LinkedList<Job> selectJobsByFisher(int fisher_id) {

        LinkedList<Integer> job_ids = new LinkedList<>();
        LinkedList<Integer> intermediary_ids = new LinkedList<>();
        LinkedList<Integer> fisher_ids = new LinkedList<>();

        for (String[] join : joins) {

            int currentJobFisher_id = Integer.parseInt(join[2]);

            if (currentJobFisher_id == fisher_id) {

                job_ids.add(Integer.parseInt(join[0]));
                intermediary_ids.add(Integer.parseInt(join[1]));
                fisher_ids.add(Integer.parseInt(join[2]));

            }

        }

        LinkedList<Job> jobsByFisher = new LinkedList<>();

        for (int i = 0; i < job_ids.size(); i++) {

            jobsByFisher.add(new Job(selectJob(job_ids.get(i)), intermediary_ids.get(i), fisher_ids.get(i)));

        }

        return jobsByFisher;

    }

    public static LinkedList<Job> selectJobsByIntermediary(int intermediary_id) {

        LinkedList<Integer> job_ids = new LinkedList<>();
        LinkedList<Integer> intermediary_ids = new LinkedList<>();

        for (String[] join : joins) {

            int currentJobIntermediary_id = Integer.parseInt(join[1]);

            if (currentJobIntermediary_id == intermediary_id) {

                job_ids.add(Integer.parseInt(join[0]));
                intermediary_ids.add(Integer.parseInt(join[1]));

            }

        }

        LinkedList<Job> jobsByIntermediary = new LinkedList<>();

        for (int i = 0; i < job_ids.size(); i++) {

            jobsByIntermediary.add(new Job(selectJob(job_ids.get(i)), intermediary_ids.get(i)));

        }

        return jobsByIntermediary;

    }

    public static Fisher selectJobReturnFisher(int job_id) {

        Fisher fisher = null;

        for (String[] join : joins) {

            if (Integer.parseInt(join[0]) == job_id) {

                fisher = selectFisherRecord(Integer.parseInt(join[2]));

            }

        }

        return fisher;

    }

    public static Intermediary selectJobReturnIntermediary(int job_id) {

        Intermediary intermediary = null;

        for (String[] join : joins) {

            if (Integer.parseInt(join[0]) == job_id) {

                intermediary = selectIntermediaryRecord(Integer.parseInt(join[1]));

            }

        }

        return intermediary;

    }

    public static void updateFisherId(int job_id, Integer fisher_id) {

        DatabaseController.updateFisherId(job_id, fisher_id);

        if (fisher_id == null) {

            fisher_id = 0;

        }

        for (String[] join : joins) {

            if (Integer.parseInt(join[0]) == job_id) {

                join[2] = String.valueOf(fisher_id);

            }

        }

        writeFiles();

    }

    //Testing
    public static void main(String[] args) {

        loadFiles();
        writeFiles();

    }

}
