
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FisherTests {

    @Test
    void testGettersAndSetters() {

        //Create test object
        Fisher fisher = new Fisher(1, "username1", "password1", "fname1", "sname1", "911111111");

        //Test getters
        assertEquals(1, fisher.getID());
        assertEquals("username1", fisher.getUsername());
        assertEquals("password1", fisher.getPassword());
        assertEquals("fname1", fisher.getFname());
        assertEquals("sname1", fisher.getSname());
        assertEquals("fname1 sname1", fisher.getFullName());
        assertEquals("911111111", fisher.getMobileNo());

        //Test setters
        fisher.setUsername("username2");
        fisher.setPassword("password2");
        fisher.setFname("fname2");
        fisher.setSname("sname2");
        fisher.setMobileNo("922222222");

        assertEquals("username2", fisher.getUsername());
        assertEquals("password2", fisher.getPassword());
        assertEquals("fname2", fisher.getFname());
        assertEquals("sname2", fisher.getSname());
        assertEquals("922222222", fisher.getMobileNo());

    }

    @Test
    void testToString() {

        //Create test object
        Fisher fisher = new Fisher(1, "username1", "password1", "fname1", "sname1", "911111111");

        //Test toString
        String expected = "Fisher{fisherId=1, username='username1', fName='fname1', sName='sname1', password='password1', mobileNo=911111111}";
        String actual = fisher.toString();
        assertEquals(expected, actual);

    }

}
