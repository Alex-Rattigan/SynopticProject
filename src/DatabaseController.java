import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.LinkedList;

// References: https://www.tutorialspoint.com/postgresql/postgresql_java.htm
public class DatabaseController
{
    private static Connection c = null;

    // Method for connecting to DB
    public static void connect()
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://alexpinorwich.ddns.net:5432/myfishingpal", "pi", "12345");
            c.setAutoCommit(false);

            System.out.println("Connection to database successful.");

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Connection to database could not be established.");
        }
    }

    // -------------------------------------------- CRUD FOR FISHERS -------------------------------------------- //

    public static void insertFisherRecord(String username, String password, String fname, String lname, String mobNo)
    {
        Statement insert = null;

        try
        {
            connect();

            insert = c.createStatement();

            String statement = "INSERT INTO Fishers (username, password, fname, lname, mobile_no)VALUES('"
                    + username + "', '" + password + "', '" + fname + "', '" + lname + "', '" + mobNo + "');";

            insert.executeUpdate(statement);

            insert.close();
            c.commit();
            c.close();

            System.out.println("Insert for FISHER successful.");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Insert statement for FISHER could not be completed.");
        }
    }

    public static Fisher checkFisherExists(String username)
    {
        Statement select = null;

        try {
            connect();

            select = c.createStatement();

            ResultSet result = select.executeQuery("SELECT * FROM Fishers WHERE username = '" + username + "';");

            while(result.next())
            {
                int fisher_id = result.getInt("fisher_id");
                String user = result.getString("username");
                String password = result.getString("password");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String mob_no = result.getString("mobile_no");

                System.out.println("ID = " + fisher_id + ", USERNAME = " + user + ", NAME = " + fname + " " + lname + ", MOBILE NO = " + mob_no);

                return new Fisher(fisher_id, username, password, fname, lname, mob_no);
            }

            result.close();
            select.close();
            c.close();

            System.out.println("Fisher with username " + username + " exists.");
        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: FISHER with username" + username + " does not exist.");
        }
        return null;
    }

    public static LinkedList<Fisher> selectAllFisherRecords()
    {
        Statement select = null;

        LinkedList<Fisher> fishers = new LinkedList<>();

        try {
            connect();

            select = c.createStatement();

            ResultSet result = select.executeQuery("SELECT * FROM Fishers;");

            while(result.next())
            {
                int fisher_id = result.getInt("fisher_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String mob_no = result.getString("mobile_no");

                System.out.println("ID = " + fisher_id + ", USERNAME = " + username + ", NAME = " + fname + " " + lname + ", MOBILE NO = " + mob_no);

                fishers.add(new Fisher(fisher_id, username, password, fname, lname, mob_no));
            }

            result.close();
            select.close();
            c.close();

            System.out.println("Select for ALL FISHERS successful.");
            return fishers;
        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: Select for ALL FISHER could not be completed.");
        }
        return null;
    }


    public static Fisher selectFisherRecord(int fisher_id)
    {
        Statement select = null;

        try
        {
            connect();

            select = c.createStatement();

            ResultSet result = select.executeQuery("SELECT * FROM Fishers WHERE fisher_id = " + fisher_id + ";");

            Fisher fisher = null;

            while(result.next())
            {
                int id = result.getInt("fisher_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String mob_no = result.getString("mobile_no");

                System.out.println("ID = " + id + ", USERNAME = " + username + ", NAME = " + fname + " " + lname + ", MOBILE NO = " + mob_no);

                fisher = new Fisher(id, username, password, fname, lname, mob_no);
            }

            result.close();
            select.close();
            c.close();

            System.out.println("Select for FISHER successful.");
            return fisher;

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Select for FISHER could not be completed.");
        }
        return null;
    }

    public static void updatePasswordFisher(int fisher_id, String new_password)
    {
        Statement update = null;

        try {
            connect();

            update = c.createStatement();

            String statement = "UPDATE Fishers SET password = '" + new_password + "' WHERE fisher_id = " + fisher_id + ";";

            update.executeUpdate(statement);
            c.commit();

            ResultSet result = update.executeQuery("SELECT * FROM Fishers WHERE fisher_id = " + fisher_id + ";");

            while(result.next())
            {
                int id = result.getInt("fisher_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String mob_no = result.getString("mobile_no");

                System.out.println("ID = " + id + ", USERNAME = " + username + ", PASSWORD = " + password + ", NAME = "
                        + fname + " " + lname + ", MOBILE NO = " + mob_no);
            }

            result.close();
            update.close();
            c.close();

            System.out.println("Update password for FISHER successful.");
        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: Update password for FISHER could not be completed.");
        }
    }

    public static void updateMobileNoFisher(int fisher_id, String new_mob_no)
    {
        Statement update = null;

        try {
            connect();

            update = c.createStatement();

            String statement = "UPDATE Fishers SET mobile_no = '" + new_mob_no + "' WHERE fisher_id = " + fisher_id + ";";

            update.executeUpdate(statement);
            c.commit();

            ResultSet result = update.executeQuery("SELECT * FROM Fishers WHERE fisher_id = " + fisher_id + ";");

            while(result.next())
            {
                int id = result.getInt("fisher_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String mob_no = result.getString("mobile_no");

                System.out.println("ID = " + id + ", USERNAME = " + username + ", PASSWORD = " + password + ", NAME = "
                        + fname + " " + lname + ", MOBILE NO = " + mob_no);
            }

            result.close();
            update.close();
            c.close();

            System.out.println("Update mobile no. for FISHER successful.");

        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: Update mobile no. for FISHER could not be completed.");
        }
    }

    public static void deleteFisherRecord(int fisher_id)
    {
        Statement delete = null;

        try {
            connect();

            delete = c.createStatement();

            String statement = "DELETE FROM Fishers WHERE fisher_id = " + fisher_id + ";";

            delete.executeUpdate(statement);
            c.commit();

            ResultSet result = delete.executeQuery("SELECT * FROM Fishers;");

            while(result.next())
            {
                int id = result.getInt("fisher_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String mob_no = result.getString("mobile_no");

                System.out.println("ID = " + id + ", USERNAME = " + username + ", PASSWORD = " + password + ", NAME = "
                        + fname + " " + lname + ", MOBILE NO = " + mob_no);
            }

            result.close();
            delete.close();
            c.close();

            System.out.println("Delete for FISHER successful.");

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Delete for FISHER could not be completed.");
        }
    }

    // ---------------------------------------- CRUD FOR INTERMEDIARIES ---------------------------------------- //

    public static void insertIntermediaryRecord(String username, String password, String fname, String lname, String mobNo)
    {
        Statement insert = null;

        try
        {
            connect();

            insert = c.createStatement();

            String statement = "INSERT INTO Intermediaries (username, password, fname, lname, mobile_no)VALUES('"
                    + username + "', '" + password + "', '" + fname + "', '" + lname + "', '" + mobNo + "');";

            insert.executeUpdate(statement);

            insert.close();
            c.commit();
            c.close();

            System.out.println("Insert for INTERMEDIARY successful.");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Insert statement for INTERMEDIARY could not be completed.");
        }
    }

    public static Intermediary checkIntermediaryExists(String username)
    {
        Statement select = null;

        try {
            connect();

            select = c.createStatement();

            ResultSet result = select.executeQuery("SELECT * FROM Intermediaries WHERE username = '" + username + "';");

            while(result.next())
            {
                int i_id = result.getInt("intermediary_id");
                String user = result.getString("username");
                String password = result.getString("password");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String mob_no = result.getString("mobile_no");

                System.out.println("ID = " + i_id + ", USERNAME = " + user + ", NAME = " + fname + " " + lname + ", MOBILE NO = " + mob_no);

                return new Intermediary(i_id, username, password, fname, lname, mob_no);
            }

            result.close();
            select.close();
            c.close();

            System.out.println("INTERMEDIARY with username " + username + " exists.");
        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: INTERMEDIARY with username " + username + " does not exist.");
        }
        return null;
    }

    public static LinkedList<Intermediary> selectAllIntermediaryRecords()
    {
        Statement select = null;

        LinkedList<Intermediary> intermediaries = new LinkedList<>();

        try {
            connect();

            select = c.createStatement();

            ResultSet result = select.executeQuery("SELECT * FROM Intermediaries;");

            while(result.next())
            {
                int intermediary_id = result.getInt("intermediary_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String mob_no = result.getString("mobile_no");

                System.out.println("ID = " + intermediary_id + ", USERNAME = " + username + ", NAME = " + fname + " " + lname + ", MOBILE NO = " + mob_no);

                intermediaries.add(new Intermediary(intermediary_id, username, password, fname, lname, mob_no));
            }

            result.close();
            select.close();
            c.close();

            System.out.println("Select for ALL INTERMEDIARY successful.");
            return intermediaries;
        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: Select for ALL INTERMEDIARY could not be completed.");
        }
        return null;
    }


    public static Intermediary selectIntermediaryRecord(int intermediary_id)
    {
        Statement select = null;

        try
        {
            connect();

            select = c.createStatement();

            ResultSet result = select.executeQuery("SELECT * FROM Intermediaries WHERE intermediary_id = " + intermediary_id + ";");

            Intermediary intermediary = null;

            while(result.next())
            {
                int id = result.getInt("intermediary_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String mob_no = result.getString("mobile_no");

                System.out.println("ID = " + id + ", USERNAME = " + username + ", NAME = " + fname + " " + lname + ", MOBILE NO = " + mob_no);

                intermediary = new Intermediary(id, username, password, fname, lname, mob_no);
            }

            result.close();
            select.close();
            c.close();

            System.out.println("Select for INTERMEDIARY successful.");
            return intermediary;

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Select for INTERMEDIARY could not be completed.");
        }
        return null;
    }

    public static void updatePasswordIntermediary(int intermediary_id, String new_password)
    {
        Statement update = null;

        try {
            connect();

            update = c.createStatement();

            String statement = "UPDATE Intermediaries SET password = '" + new_password + "' WHERE intermediary_id = " + intermediary_id + ";";

            update.executeUpdate(statement);
            c.commit();

            ResultSet result = update.executeQuery("SELECT * FROM Intermediaries WHERE intermediary_id = " + intermediary_id + ";");

            while(result.next())
            {
                int id = result.getInt("intermediary_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String mob_no = result.getString("mobile_no");

                System.out.println("ID = " + id + ", USERNAME = " + username + ", PASSWORD = " + password + ", NAME = "
                        + fname + " " + lname + ", MOBILE NO = " + mob_no);
            }

            result.close();
            update.close();
            c.close();

            System.out.println("Update password for INTERMEDIARY successful.");
        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: Update password for INTERMEDIARY could not be completed.");
        }
    }

    public static void updateMobileNoIntermediary(int intermediary_id, String new_mob_no)
    {
        Statement update = null;

        try {
            connect();

            update = c.createStatement();

            String statement = "UPDATE Intermediaries SET mobile_no = '" + new_mob_no + "' WHERE intermediary_id = " + intermediary_id + ";";

            update.executeUpdate(statement);
            c.commit();

            ResultSet result = update.executeQuery("SELECT * FROM Intermediaries WHERE intermediary_id = " + intermediary_id + ";");

            while(result.next())
            {
                int id = result.getInt("intermediary_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String mob_no = result.getString("mobile_no");

                System.out.println("ID = " + id + ", USERNAME = " + username + ", PASSWORD = " + password + ", NAME = "
                        + fname + " " + lname + ", MOBILE NO = " + mob_no);
            }

            result.close();
            update.close();
            c.close();

            System.out.println("Update mobile no. for INTERMEDIARY successful.");

        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: Update mobile no. for INTERMEDIARY could not be completed.");
        }
    }

    public static void deleteIntermediaryRecord(int intermediary_id)
    {
        Statement delete = null;

        try {
            connect();

            delete = c.createStatement();

            String statement = "DELETE FROM Intermediaries WHERE intermediary_id = " + intermediary_id + ";";

            delete.executeUpdate(statement);
            c.commit();

            ResultSet result = delete.executeQuery("SELECT * FROM Intermediaries;");

            while(result.next())
            {
                int id = result.getInt("intermediary_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String mob_no = result.getString("mobile_no");

                System.out.println("ID = " + id + ", USERNAME = " + username + ", PASSWORD = " + password + ", NAME = "
                        + fname + " " + lname + ", MOBILE NO = " + mob_no);
            }

            result.close();
            delete.close();
            c.close();

            System.out.println("Delete for INTERMEDIARY successful.");

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Delete for INTERMEDIARY could not be completed.");
        }
    }

    // --------------------------------------------- CRUD FOR JOBS --------------------------------------------- //

    public static int insertJob(int intermediary_id, String fish_type, int amount_kg, double pay_per_kg, Date date_created, Date date_due, String description, boolean is_completed)
    {
        Statement insert = null;
        int j_id = 0;

        try {
            connect();

            insert = c.createStatement();

            String statement = "INSERT INTO Jobs (fish_type, amount_kg, pay_per_kg, date_created, date_due, description, is_completed)VALUES('"
                     + fish_type + "', " + amount_kg + ", " + pay_per_kg + ", '" + date_created + "', '"+ date_due + "', '" + description
                    + "', " + is_completed + ") RETURNING job_id;";

            insert.execute(statement);

            ResultSet job = insert.getResultSet();

            while(job.next())
            {
                j_id = job.getInt("job_id");
            }

            String statement2 = "INSERT INTO Fishers_Inters_Jobs(job_id, intermediary_id)VALUES(" + j_id + ", " + intermediary_id + ");";
            insert.executeUpdate(statement2);

            insert.close();
            c.commit();
            c.close();

            System.out.println("Insert for JOB successful.");

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Insert for JOB could not be completed.");
        }

        return j_id;
    }

    public static LinkedList<Job> selectAllJobs()
    {
        Statement select = null;

        LinkedList<Job> jobs = new LinkedList<>();

        try {
            connect();

            select = c.createStatement();

            ResultSet result = select.executeQuery("SELECT * FROM Jobs;");

            while(result.next())
            {
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

                jobs.add(new Job(fish_type, amount_kg, pay_per_kg, date_created, date_due, description, is_completed));
            }

            result.close();
            select.close();
            c.close();

            System.out.println("Select for ALL JOBS successful.");
            return jobs;
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Select for ALL JOBS could not be completed.");
        }
        return null;
    }

    public static Job selectJob(int job_id)
    {
        Statement select = null;

        try {
            connect();

            select = c.createStatement();

            ResultSet result = select.executeQuery("SELECT * FROM Jobs WHERE job_id = " + job_id + ";");

            Job job = null;

            while(result.next())
            {
                int id = result.getInt("job_id");
                String fish_type = result.getString("fish_type");
                int amount_kg = result.getInt("amount_kg");
                double pay_per_kg = result.getDouble("pay_per_kg");
                Date date_created = result.getDate("date_created");
                Date date_due = result.getDate("date_due");
                String description = result.getString("description");
                boolean is_completed = result.getBoolean("is_completed");

                System.out.println("ID = " + id + ", FISH TYPE = " + fish_type + ", AMOUNT (KG) = " + amount_kg
                        + ", PAY PER KG = " + pay_per_kg + ", DATE CREATED = " + date_created + ", DATE DUE = "
                        + date_due + ", DESCRIPTION = " + description + ", COMPLETED? = " + is_completed );

                job = new Job(fish_type, amount_kg, pay_per_kg, date_created, date_due, description, is_completed);
            }

            result.close();
            select.close();
            c.close();

            System.out.println("Select for JOB successful.");
            return job;
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Select for JOB could not be completed.");
        }
        return null;
    }

    public static void updateFishType(int job_id, String new_type)
    {
        Statement update = null;

        try {
            connect();

            update = c.createStatement();

            String statement = "UPDATE Jobs SET fish_type = '" + new_type + "' WHERE job_id = " + job_id + ";";

            update.executeUpdate(statement);
            c.commit();

            ResultSet result = update.executeQuery("SELECT * FROM Jobs WHERE job_id = " + job_id + ";");

            while(result.next())
            {
                int id = result.getInt("job_id");
                String fish_type = result.getString("fish_type");
                int amount_kg = result.getInt("amount_kg");
                double pay_per_kg = result.getDouble("pay_per_kg");
                Date date_created = result.getDate("date_created");
                Date date_due = result.getDate("date_due");
                String description = result.getString("description");
                boolean is_completed = result.getBoolean("is_completed");

                System.out.println("ID = " + id + ", FISH TYPE = " + fish_type + ", AMOUNT (KG) = " + amount_kg
                        + ", PAY PER KG = " + pay_per_kg + ", DATE CREATED = " + date_created + ", DATE DUE = "
                        + date_due + ", DESCRIPTION = " + description + ", COMPLETED? = " + is_completed );
            }

            result.close();
            update.close();
            c.close();

            System.out.println("Update FISH_TYPE for JOB successful.");

        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: Update for FISH_TYPE could not be completed.");
        }
    }

    public static void updateAmountKg(int job_id, int new_amount)
    {
        Statement update = null;

        try {
            connect();

            update = c.createStatement();

            String statement = "UPDATE Jobs SET amount_kg = " + new_amount + " WHERE job_id = " + job_id + ";";

            update.executeUpdate(statement);
            c.commit();

            ResultSet result = update.executeQuery("SELECT * FROM Jobs WHERE job_id = " + job_id + ";");

            while(result.next())
            {
                int id = result.getInt("job_id");
                String fish_type = result.getString("fish_type");
                int amount_kg = result.getInt("amount_kg");
                double pay_per_kg = result.getDouble("pay_per_kg");
                Date date_created = result.getDate("date_created");
                Date date_due = result.getDate("date_due");
                String description = result.getString("description");
                boolean is_completed = result.getBoolean("is_completed");

                System.out.println("ID = " + id + ", FISH TYPE = " + fish_type + ", AMOUNT (KG) = " + amount_kg
                        + ", PAY PER KG = " + pay_per_kg + ", DATE CREATED = " + date_created + ", DATE DUE = "
                        + date_due + ", DESCRIPTION = " + description + ", COMPLETED? = " + is_completed );
            }

            result.close();
            update.close();
            c.close();

            System.out.println("Update AMOUNT_KG for JOB successful.");

        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: Update for AMOUNT_KG could not be completed.");
        }
    }

    public static void updatePay(int job_id, double new_pay)
    {
        Statement update = null;

        try {
            connect();

            update = c.createStatement();

            String statement = "UPDATE Jobs SET pay_per_kg = " + new_pay + " WHERE job_id = " + job_id + ";";

            update.executeUpdate(statement);
            c.commit();

            ResultSet result = update.executeQuery("SELECT * FROM Jobs WHERE job_id = " + job_id + ";");

            while(result.next())
            {
                int id = result.getInt("job_id");
                String fish_type = result.getString("fish_type");
                int amount_kg = result.getInt("amount_kg");
                double pay_per_kg = result.getDouble("pay_per_kg");
                Date date_created = result.getDate("date_created");
                Date date_due = result.getDate("date_due");
                String description = result.getString("description");
                boolean is_completed = result.getBoolean("is_completed");

                System.out.println("ID = " + id + ", FISH TYPE = " + fish_type + ", AMOUNT (KG) = " + amount_kg
                        + ", PAY PER KG = " + pay_per_kg + ", DATE CREATED = " + date_created + ", DATE DUE = "
                        + date_due + ", DESCRIPTION = " + description + ", COMPLETED? = " + is_completed );
            }

            result.close();
            update.close();
            c.close();

            System.out.println("Update PAY_PER_KG for JOB successful.");

        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: Update for PAY_PER_KG could not be completed.");
        }
    }

    public static void updateDateDue(int job_id, Date new_date)
    {
        Statement update = null;

        try {
            connect();

            update = c.createStatement();

            String statement = "UPDATE Jobs SET date_due = '" + new_date + "' WHERE job_id = " + job_id + ";";

            update.executeUpdate(statement);
            c.commit();

            ResultSet result = update.executeQuery("SELECT * FROM Jobs WHERE job_id = " + job_id + ";");

            while(result.next())
            {
                int id = result.getInt("job_id");
                String fish_type = result.getString("fish_type");
                int amount_kg = result.getInt("amount_kg");
                double pay_per_kg = result.getDouble("pay_per_kg");
                Date date_created = result.getDate("date_created");
                Date date_due = result.getDate("date_due");
                String description = result.getString("description");
                boolean is_completed = result.getBoolean("is_completed");

                System.out.println("ID = " + id + ", FISH TYPE = " + fish_type + ", AMOUNT (KG) = " + amount_kg
                        + ", PAY PER KG = " + pay_per_kg + ", DATE CREATED = " + date_created + ", DATE DUE = "
                        + date_due + ", DESCRIPTION = " + description + ", COMPLETED? = " + is_completed );
            }

            result.close();
            update.close();
            c.close();

            System.out.println("Update DATE_DUE for JOB successful.");

        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: Update for DATE_DUE could not be completed.");
        }
    }

    public static void updateDescription(int job_id, String new_description)
    {
        Statement update = null;

        try {
            connect();

            update = c.createStatement();

            String statement = "UPDATE Jobs SET date_due = '" + new_description + "' WHERE job_id = " + job_id + ";";

            update.executeUpdate(statement);
            c.commit();

            ResultSet result = update.executeQuery("SELECT * FROM Jobs WHERE job_id = " + job_id + ";");

            while(result.next())
            {
                int id = result.getInt("job_id");
                String fish_type = result.getString("fish_type");
                int amount_kg = result.getInt("amount_kg");
                double pay_per_kg = result.getDouble("pay_per_kg");
                Date date_created = result.getDate("date_created");
                Date date_due = result.getDate("date_due");
                String description = result.getString("description");
                boolean is_completed = result.getBoolean("is_completed");

                System.out.println("ID = " + id + ", FISH TYPE = " + fish_type + ", AMOUNT (KG) = " + amount_kg
                        + ", PAY PER KG = " + pay_per_kg + ", DATE CREATED = " + date_created + ", DATE DUE = "
                        + date_due + ", DESCRIPTION = " + description + ", COMPLETED? = " + is_completed );
            }

            result.close();
            update.close();
            c.close();

            System.out.println("Update DESCRIPTION for JOB successful.");

        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: Update for DESCRIPTION could not be completed.");
        }
    }

    public static void updateCompleted(int job_id, boolean is_completed)
    {
        Statement update = null;

        try {
            connect();

            update = c.createStatement();

            String statement = "UPDATE Jobs SET is_completed = '" + is_completed + "' WHERE job_id = " + job_id + ";";

            update.executeUpdate(statement);
            c.commit();

            ResultSet result = update.executeQuery("SELECT * FROM Jobs WHERE job_id = " + job_id + ";");

            while(result.next())
            {
                int id = result.getInt("job_id");
                String fish_type = result.getString("fish_type");
                int amount_kg = result.getInt("amount_kg");
                double pay_per_kg = result.getDouble("pay_per_kg");
                Date date_created = result.getDate("date_created");
                Date date_due = result.getDate("date_due");
                String description = result.getString("description");
                boolean completed = result.getBoolean("is_completed");

                System.out.println("ID = " + id + ", FISH TYPE = " + fish_type + ", AMOUNT (KG) = " + amount_kg
                        + ", PAY PER KG = " + pay_per_kg + ", DATE CREATED = " + date_created + ", DATE DUE = "
                        + date_due + ", DESCRIPTION = " + description + ", COMPLETED? = " + completed );
            }

            result.close();
            update.close();
            c.close();

            System.out.println("Update IS_COMPLETED for JOB successful.");

        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: Update for IS_COMPLETED could not be completed.");
        }
    }

    public static void deleteJob(int job_id)
    {
        Statement delete = null;

        try {
            connect();

            delete = c.createStatement();

            String statement = "DELETE ON CASCADE FROM Jobs WHERE job_id = " + job_id + ";";

            delete.executeUpdate(statement);
            c.commit();

            ResultSet result = delete.executeQuery("SELECT * FROM Jobs;");

            while(result.next())
            {
                int id = result.getInt("job_id");
                String fish_type = result.getString("fish_type");
                int amount_kg = result.getInt("amount_kg");
                double pay_per_kg = result.getDouble("pay_per_kg");
                Date date_created = result.getDate("date_created");
                Date date_due = result.getDate("date_due");
                String description = result.getString("description");
                boolean is_completed = result.getBoolean("is_completed");

                System.out.println("ID = " + id + ", FISH TYPE = " + fish_type + ", AMOUNT (KG) = " + amount_kg
                        + ", PAY PER KG = " + pay_per_kg + ", DATE CREATED = " + date_created + ", DATE DUE = "
                        + date_due + ", DESCRIPTION = " + description + ", COMPLETED? = " + is_completed );
            }

            result.close();
            delete.close();
            c.close();

            System.out.println("Delete for JOB successful.");

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Delete for JOB could not be completed.");
        }
    }

    // ------------------------------ CRUD FOR FISHERS + INTERMEDIARIES + JOBS -------------------------------- //

    public static void insertFisherIntermediaryJob(int job_id, int intermediary_id, Integer fisher_id)
    {
        Statement insert = null;

        try {
            connect();

            insert = c.createStatement();

            String statement = "INSERT INTO Fishers_Inters_Jobs(job_id, intermediary_id, fisher_id)VALUES("
                    + job_id + ", " + intermediary_id + ", " + fisher_id + ");";

            insert.executeUpdate(statement);

            insert.close();
            c.commit();
            c.close();

            System.out.println("Insert for FISHER_INTERMEDIARY_JOB successful.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Insert for FISHER_INTERMEDIARY_JOB could not be completed.");
        }
    }

    public static LinkedList<Job> selectJobsByFisher(int fisher_id)
    {
        Statement select = null;

        try {
            connect();

            select = c.createStatement();

            ResultSet result = select.executeQuery("SELECT * FROM Fishers_Inters_Jobs WHERE fisher_id = " + fisher_id + ";");

            LinkedList<Integer> job_ids = new LinkedList<>();
            LinkedList<Integer> i_ids = new LinkedList<>();
            LinkedList<Integer> f_ids = new LinkedList<>();

            while(result.next())
            {
                int job_id = result.getInt("job_id");
                int i_id = result.getInt("intermediary_id");
                int f_id = result.getInt("fisher_id");

                job_ids.add(job_id);
                i_ids.add(i_id);
                f_ids.add(f_id);
            }

            result.close();

            LinkedList<Job> jobs = new LinkedList<>();

            for(int i = 0; i < job_ids.size(); i++)
            {
                ResultSet result2 = select.executeQuery("SELECT * FROM Jobs WHERE job_id = " + job_ids.get(i) + ";");

                while(result2.next())
                {
                    String fish_type = result2.getString("fish_type");
                    int amount_kg = result2.getInt("amount_kg");
                    double pay_per_kg = result2.getDouble("pay_per_kg");
                    Date date_created = result2.getDate("date_created");
                    Date date_due = result2.getDate("date_due");
                    String description = result2.getString("description");
                    boolean is_completed = result2.getBoolean("is_completed");

                    System.out.println("ID = " + job_ids.get(i) + ", FISH TYPE = " + fish_type + ", AMOUNT (KG) = " + amount_kg
                            + ", PAY PER KG = " + pay_per_kg + ", DATE CREATED = " + date_created + ", DATE DUE = "
                            + date_due + ", DESCRIPTION = " + description + ", COMPLETED? = " + is_completed );

                    jobs.add(new Job(job_ids.get(i), fish_type, amount_kg, pay_per_kg, date_created, date_due, description, is_completed, i_ids.get(i), f_ids.get(i)));
                }

                result2.close();
            }

            select.close();
            c.close();

            System.out.println("Select for JOBS BY FISHER successful.");
            return jobs;

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Select for ALL JOBS BY FISHER could not be completed.");
        }
        return null;
    }

    public static LinkedList<Job> selectJobByIntermediary(int intermediary_id)
    {
        Statement select = null;

        try {
            connect();

            select = c.createStatement();

            ResultSet result = select.executeQuery("SELECT * FROM Fishers_Inters_Jobs WHERE intermediary_id = " + intermediary_id + ";");

            LinkedList<Integer> job_ids = new LinkedList<>();
            LinkedList<Integer> i_ids = new LinkedList<>();
            LinkedList<Integer> f_ids = new LinkedList<>();

            while(result.next())
            {
                int job_id = result.getInt("job_id");
                int i_id = result.getInt("intermediary_id");
                int f_id = result.getInt("fisher_id");

                job_ids.add(job_id);
                i_ids.add(i_id);
                f_ids.add(f_id);
            }

            result.close();

            LinkedList<Job> jobs = new LinkedList<>();

            for(int i = 0; i < job_ids.size(); i++)
            {
                ResultSet result2 = select.executeQuery("SELECT * FROM Jobs WHERE job_id = " + job_ids.get(i) + ";");

                while(result2.next())
                {
                    String fish_type = result2.getString("fish_type");
                    int amount_kg = result2.getInt("amount_kg");
                    double pay_per_kg = result2.getDouble("pay_per_kg");
                    Date date_created = result2.getDate("date_created");
                    Date date_due = result2.getDate("date_due");
                    String description = result2.getString("description");
                    boolean is_completed = result2.getBoolean("is_completed");

                    System.out.println("ID = " + job_ids.get(i) + ", FISH TYPE = " + fish_type + ", AMOUNT (KG) = " + amount_kg
                            + ", PAY PER KG = " + pay_per_kg + ", DATE CREATED = " + date_created + ", DATE DUE = "
                            + date_due + ", DESCRIPTION = " + description + ", COMPLETED? = " + is_completed );

                    jobs.add(new Job(job_ids.get(i), fish_type, amount_kg, pay_per_kg, date_created, date_due, description, is_completed, i_ids.get(i), f_ids.get(i)));
                }

                result2.close();
            }

            select.close();
            c.close();

            System.out.println("Select for JOBS BY INTERMEDIARY successful.");
            return jobs;

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Select for ALL JOBS BY INTERMEDIARY could not be completed.");
        }
        return null;
    }

    public static void updateFisherId(int job_id, Integer fisher_id)
    {
        Statement update = null;

        try {
            connect();

            update = c.createStatement();

            String statement = "UPDATE Fishers_Inters_Jobs SET fisher_id = '" + fisher_id + "' WHERE job_id = " + job_id + ";";

            update.executeUpdate(statement);
            c.commit();

            ResultSet result = update.executeQuery("SELECT * FROM Fishers_Inters_Jobs WHERE fisher_id = " + fisher_id + ";");

            while(result.next())
            {
                int j_id = result.getInt("job_id");
                int i_id = result.getInt("intermediary_id");
                int f_id = result.getInt("fisher_id");

                System.out.println("JOB ID = " + j_id + ", INTERMEDIARY ID = " + i_id + ", FISHER ID = " + f_id);
            }

            result.close();
            update.close();
            c.close();

            System.out.println("Update FISH_TYPE for JOB successful.");

        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("ERROR: Update for FISH_TYPE could not be completed.");
        }
    }

    public static void main(String[] args)
    {
       // Testing
        /* insertFisherRecord("fishboy69", "Epic2012", "fisher", "man", "0769696969");
        Fisher fisher = selectFisherRecord(1);

        insertIntermediaryRecord("notfishboy69", "Epicer2012", "man", "man", "0769696968");
        Intermediary intermediary = selectIntermediaryRecord(1);

        insertJob(intermediary.getID(), "fish", 5, 3.4, Date.valueOf("2021-06-05"), Date.valueOf("2021-06-10"), false);
        Job job = selectJob(1);

        selectJobsByFisher(1);

        insertJob(intermediary.getID(), "fish", 7, 4.2, Date.valueOf("2021-06-05"), Date.valueOf("2021-06-10"), false);
        selectJobByIntermediary(1);

        updateFisherId(1, 1);*/

        //updateFisherId(1, 16);

        //insertJob(1, "fish", 7, 4.2, Date.valueOf("2021-06-05"), Date.valueOf("2021-06-10"), "this is a job",true);

        insertJob(1, "fish", 7, 4.2, Date.valueOf("2021-06-08"), Date.valueOf("2021-06-15"), "this is a job",false);

        updateFisherId(3, 16);
    }
}
