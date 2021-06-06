import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class CSVController {

    private static final String fishers = "Fishers.mfp";
    private static final String intermediaries = "Intermediaries.mfp";
    private static final String jobs = "Jobs.mfp";
    private static final String joins = "Joins.mfp";

    private static final ArrayList<String[]> fishersArrayList = new ArrayList<>();
    private static final ArrayList<String[]> intermediariesArrayList = new ArrayList<>();
    private static final ArrayList<String[]> jobsArrayList = new ArrayList<>();
    private static final ArrayList<String[]> joinsArrayList = new ArrayList<>();

    public static void loadFiles() {

        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(fishers));
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                fishersArrayList.add(line.split(","));

            }
            bufferedReader.close();

            bufferedReader = new BufferedReader(new FileReader(intermediaries));
            while ((line = bufferedReader.readLine()) != null) {

                intermediariesArrayList.add(line.split(","));

            }
            bufferedReader.close();

            bufferedReader = new BufferedReader(new FileReader(jobs));
            while ((line = bufferedReader.readLine()) != null) {

                jobsArrayList.add(line.split(","));

            }
            bufferedReader.close();

            bufferedReader = new BufferedReader(new FileReader(joins));
            while ((line = bufferedReader.readLine()) != null) {

                joinsArrayList.add(line.split(","));

            }
            bufferedReader.close();

        } catch (Exception e) {

            //e.printStackTrace();
            System.out.println(fishers + ", " + intermediaries + ", " + jobs + ", or " + joins + " failed to read.");

        }

    }

    public static int insertCachedFisherRecord(String username, String password, String fname, String lname, String mobNo) {

        int id = DatabaseController.insertFisherRecord(username, password, fname, lname, mobNo);
        fishersArrayList.add(new String[]{String.valueOf(id), username, password, fname, lname, mobNo});
        return id;

    }

    public static LinkedList<Fisher> selectAllCachedFisherRecords() {

        LinkedList<Fisher> fishersLinkedList = new LinkedList<>();
        for (String[] strings : fishersArrayList) {

            fishersLinkedList.add(new Fisher(Integer.parseInt(strings[0]), strings[1], strings[2], strings[3], strings[4], strings[5]));

        }
        return fishersLinkedList;

    }

}
