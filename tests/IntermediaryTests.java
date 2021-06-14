
/*
 *
 * File:        IntermediaryTests.java
 *
 * Date:        18/06/2021
 *
 * Author:      Alex Rattigan
 *
 * Description: Provides a test for every method in Intermediary.java
 *
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntermediaryTests {

    @Test
    public void testGettersAndSetters() {

        //Create test object
        Intermediary intermediary = new Intermediary(1, "username1", "password1",
                "fname1", "sname1", "911111111");

        //Test getters
        assertEquals(1, intermediary.getID());
        assertEquals("username1", intermediary.getUsername());
        assertEquals("password1", intermediary.getPassword());
        assertEquals("fname1", intermediary.getFname());
        assertEquals("sname1", intermediary.getSname());
        assertEquals("911111111", intermediary.getMobileNo());

        //Test setters
        intermediary.setUsername("username2");
        intermediary.setPassword("password2");
        intermediary.setFname("fname2");
        intermediary.setSname("sname2");
        intermediary.setMobileNo("922222222");

        assertEquals("username2", intermediary.getUsername());
        assertEquals("password2", intermediary.getPassword());
        assertEquals("fname2", intermediary.getFname());
        assertEquals("sname2", intermediary.getSname());
        assertEquals("922222222", intermediary.getMobileNo());

    }

    @Test
    public void testToString() {

        //Create test object
        Fisher fisher = new Fisher(1, "username1", "password1", "fname1", "sname1",
                "911111111");

        //Test toString
        String expected = "Fisher{fisherId=1, username='username1', fName='fname1', sName='sname1', " +
                "password='password1', mobileNo=911111111}";
        String actual = fisher.toString();
        assertEquals(expected, actual);

    }

}
