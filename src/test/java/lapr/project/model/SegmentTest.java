/**
 * Package location for Model concept tests.
 */
package lapr.project.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for segment class
 *
 * @author Daniel Gonçalves - 1151452
 * @author Eric Amaral - 1141570
 * @author Ivo Ferro - 1151159
 * @author João Pereira - 1151241
 * @author Tiago Correia - 1151031
 */
public class SegmentTest {

    /**
     * The instance to be tested.
     */
    private Segment instance;

    /**
     * The epsilon of the allowed error.
     */
    private final static Double EPSILON = 0.01d;

    @Before
    public void setUp() {
        instance = new Segment();
    }

    /**
     * Test of getIdentification and setIdentification method, of class Segment.
     */
    @Test
    public void testGetSetIdentification() {
        System.out.println("get and setIdentification");

        String identification = "";
        instance.setIdentification(identification);

        assertEquals(instance.getIdentification(), identification);
    }

    /**
     * Test of getAllowedAltitudes method, of class Segment.
     */
    @Test
    public void testGetSetAllowedAltitudes() {
        System.out.println("setAllowedAltitudes");

        List<Double> allowedAltitudes = new ArrayList<>();
        allowedAltitudes.add(100d);
        allowedAltitudes.add(125d);
        allowedAltitudes.add(145.55d);

        instance.setAllowedAltitudes(allowedAltitudes);

        assertEquals(instance.getAllowedAltitudes(), allowedAltitudes);
    }

    /**
     * Test of getWindDirection method, of class Segment.
     */
    @Test
    public void testGetSetWindDirection() {
        System.out.println("get and setWindDirection");

        Double windDirection = 75.5d;

        instance.setWindDirection(windDirection);

        assertEquals(instance.getWindDirection(), windDirection, EPSILON);
    }

    /**
     * Test of getWindSpeed method, of class Segment.
     */
    @Test
    public void testGetSetWindSpeed() {
        System.out.println("get and setWindSpeed");

        Double windSpeed = 113.13d;

        instance.setWindIntensity(windSpeed);

        assertEquals(instance.getWindIntensity(), windSpeed, EPSILON);
    }

    /**
     * Test of equals method, of class Segment.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");

        Object obj = null;

        assertFalse(instance.equals(obj));

        obj = new Segment();

        assertTrue(instance.equals(obj));
    }
}
