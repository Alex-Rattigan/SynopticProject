
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;

public class SyncMain {

    private static Connection connection = null;

    private static final String fishersFile = "Fishers.mfp";
    private static final String intermediariesFile = "Intermediaries.mfp";
    private static final String jobsFile = "Jobs.mfp";
    private static final String joinsFile = "Joins.mfp";

    private static ArrayList<String[]> fishers = new ArrayList<>();
    private static ArrayList<String[]> intermediaries = new ArrayList<>();
    private static ArrayList<String[]> jobs = new ArrayList<>();
    private static ArrayList<String[]> joins = new ArrayList<>();

    // Method for connecting to DB
    private static void connect() {

        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://alexpinorwich.ddns.net:5432/myfishingpal", "pi", "12345");
            connection.setAutoCommit(false);

            System.out.println("Connection to database successful.");

        } catch(Exception e) {

            e.printStackTrace();
            System.out.println("ERROR: Connection to database could not be established.");

        }

    }

    // Get all Fishers as String[]s
    private static ArrayList<String[]> selectAllFisherRecords() {

        Statement select = null;

        ArrayList<String[]> fishers = new ArrayList<>();

        try {

            connect();

            select = connection.createStatement();

            ResultSet result = select.executeQuery("SELECT * FROM Fishers;");

            while(result.next()) {

                int fisher_id = result.getInt("fisher_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String mob_no = result.getString("mobile_no");

                System.out.println("ID = " + fisher_id + ", USERNAME = " + username + ", NAME = " + fname + " " + lname + ", MOBILE NO = " + mob_no);

                fishers.add(new String[]{String.valueOf(fisher_id), username, password, fname, lname, mob_no});

            }

            result.close();
            select.close();
            connection.close();

            System.out.println("Select for ALL FISHERS successful.");
            return fishers;

        } catch(Exception e) {

            e.printStackTrace();
            System.out.println("ERROR: Select for ALL FISHER could not be completed.");

        }

        return null;

    }

    // Get all Intermediaries as String[]s
    private static ArrayList<String[]> selectAllIntermediaryRecords() {

        Statement select = null;

        ArrayList<String[]> intermediaries = new ArrayList<>();

        try {

            connect();

            select = connection.createStatement();

            ResultSet result = select.executeQuery("SELECT * FROM Intermediaries;");

            while(result.next()) {

                int intermediary_id = result.getInt("intermediary_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String mob_no = result.getString("mobile_no");

                System.out.println("ID = " + intermediary_id + ", USERNAME = " + username + ", NAME = " + fname + " " + lname + ", MOBILE NO = " + mob_no);

                intermediaries.add(new String[]{String.valueOf(intermediary_id), username, password, fname, lname, mob_no});

            }

            result.close();
            select.close();
            connection.close();

            System.out.println("Select for ALL INTERMEDIARY successful.");
            return intermediaries;

        } catch(Exception e) {

            e.printStackTrace();
            System.out.println("ERROR: Select for ALL INTERMEDIARY could not be completed.");

        }

        return null;

    }

    // Get all Jobs as String[]s
    private static ArrayList<String[]> selectAllJobs() {

        Statement select = null;

        ArrayList<String[]> jobs = new ArrayList<>();

        try {

            connect();

            select = connection.createStatement();

            ResultSet result = select.executeQuery("SELECT * FROM Jobs;");

            while(result.next()) {

                int job_id = result.getInt("job_id");
                String fish_type = result.getString("fish_type");
                int amount_kg = result.getInt("amount_kg");
                double pay_per_kg = result.getDouble("pay_per_kg");
                Date date_created = result.getDate("date_created");
                Date date_due = result.getDate("date_due");
                String description = result.getString("description");
                boolean is_completed = result.getBoolean("is_completed");

                System.out.println("ID = " + job_id + ", FISH TYPE = " + fish_type + ", AMOUNT (KG) = " + amount_kg
                        + ", PAY PER KG = " + pay_per_kg + ", DATE CREATED = " + date_created + ", DATE DUE = "
                        + date_due + ", DESCRIPTION = " + description + ", COMPLETED? = " + is_completed );

                jobs.add(new String[]{String.valueOf(job_id), fish_type, String.valueOf(amount_kg), String.valueOf(pay_per_kg), String.valueOf(date_created), String.valueOf(date_due), description, String.valueOf(is_completed)});

            }

            result.close();
            select.close();
            connection.close();

            System.out.println("Select for ALL JOBS successful.");
            return jobs;

        } catch(Exception e) {

            e.printStackTrace();
            System.out.println("ERROR: Select for ALL JOBS could not be completed.");

        }

        return null;

    }

    // Get all Joins as String[]s
    private static ArrayList<String[]> selectAllJoins() {

        Statement select = null;

        ArrayList<String[]> joins = new ArrayList<>();

        try {

            connect();

            select = connection.createStatement();

            ResultSet result = select.executeQuery("SELECT * FROM Fishers_Inters_Jobs;");

            while(result.next()) {

                int job_id = result.getInt("job_id");
                int intermediary_id = result.getInt("intermediary_id");
                Integer fisher_id = result.getInt("fisher_id");

                System.out.println("" + fisher_id);

                System.out.println("Job ID = " + job_id + ", Intermediary ID = " + intermediary_id + ", Fisher ID = " + fisher_id);

                joins.add(new String[]{String.valueOf(job_id), String.valueOf(intermediary_id), String.valueOf(fisher_id)});

            }

            result.close();
            select.close();
            connection.close();

            System.out.println("Select for ALL JOINS successful.");
            return joins;

        } catch(Exception e) {

            e.printStackTrace();
            System.out.println("ERROR: Select for ALL JOINS could not be completed.");

        }

        return null;

    }

    private static void writeFiles() {

        try {

            FileWriter fileWriter = new FileWriter(fishersFile);
            for (String[] fisher : fishers) {

                fileWriter.write(fisher[0] + "," + fisher[1] + "," + fisher[2] + "," + fisher[3] + "," + fisher[4] + "," + fisher[5] + "\n");

            }
            fileWriter.flush();
            fileWriter.close();

            fileWriter = new FileWriter(intermediariesFile);
            for (String[] intermediary : intermediaries) {

                fileWriter.write(intermediary[0] + "," + intermediary[1] + "," + intermediary[2] + "," + intermediary[3] + "," + intermediary[4] + "," + intermediary[5] + "\n");

            }
            fileWriter.flush();
            fileWriter.close();

            fileWriter = new FileWriter(jobsFile);
            for (String[] job : jobs) {

                fileWriter.write(job[0] + "," + job[1] + "," + job[2] + "," + job[3] + "," + job[4] + "," + job[5] + "," + job[6] + "," + job[7] + "\n");

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

            //e.printStackTrace();
            System.out.println(fishersFile + ", " + intermediariesFile + ", " + jobsFile + ", or " + joinsFile + " failed to write.");

        }

    }

    public static void main(String[] args) {

        fishers = selectAllFisherRecords();
        intermediaries = selectAllIntermediaryRecords();
        jobs = selectAllJobs();
        joins = selectAllJoins();

        writeFiles();

    }

}
