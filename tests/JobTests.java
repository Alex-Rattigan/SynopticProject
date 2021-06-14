
/*
 *
 * File:        JobTests.java
 *
 * Date:        18/06/2021
 *
 * Author:      Alex Rattigan
 *
 * Description: Provides a test for every method in Job.java
 *
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

public class JobTests {

    @Test
    public void testGettersAndSetters() {

        //Create test object
        Job job = new Job(1, "fish1", 1, 1.1, Date.valueOf("2001-01-01"),
                Date.valueOf("2010-10-10"), "description1", false);

        //Test getters
        assertEquals(1, job.getId());
        assertEquals("fish1", job.getFishType());
        assertEquals(1, job.getAmountKg());
        assertEquals(1.1, job.getPayPerKg());
        assertEquals(Date.valueOf("2001-01-01"), job.getDateCreated());
        assertEquals(Date.valueOf("2010-10-10"), job.getDateDue());
        assertEquals("10/10/2010", job.getFormattedDueDate());
        assertEquals("description1", job.getDescription());
        assertFalse(job.isCompleted());

        //Test setters
        job.setFishType("fish2");
        job.setAmountKg(2);
        job.setPayPerKg(2.2);
        job.setDateDue(Date.valueOf("2020-10-10"));
        job.setDescription("description2");
        job.setCompleted(true);

        assertEquals("fish2", job.getFishType());
        assertEquals(2, job.getAmountKg());
        assertEquals(2.2, job.getPayPerKg());
        assertEquals(Date.valueOf("2020-10-10"), job.getDateDue());
        assertEquals("description2", job.getDescription());
        assertTrue(job.isCompleted());

    }

    @Test
    public void testToString() {

        //Create test object
        Job job = new Job(1, "fish1", 1, 1.1, Date.valueOf("2001-01-01"),
                Date.valueOf("2010-10-10"), "description1", false);

        //Test toString
        String expected = "ID = 1, FISH TYPE = fish1, AMOUNT IN KG = 1, PAY PER KG = 1.1, DATE SET = 2001-01-01, " +
                "DATE DUE = 2010-10-10, COMPLETED? = false";
        String actual = job.toString();
        assertEquals(expected, actual);

    }

}
