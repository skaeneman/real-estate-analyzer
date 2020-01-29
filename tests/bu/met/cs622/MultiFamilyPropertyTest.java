package bu.met.cs622;

import bu.met.cs622.MultiFamilyProperty;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class MultiFamilyPropertyTest {

    MultiFamilyProperty multi = new MultiFamilyProperty(4.7, 3000, 300000,
                                                        30, 1200, 15000,
                                                        1200, 6000, 850000,
                                                        90000);

    @Test
    public void propertySquareFootage() {
        String response = multi.propertySquareFootage();
        assertEquals("Square feet: 3000.0 \nmulti-family interest rate: 4.7%", response);
    }

    // test accessor's and mutator's (getters and setters)
    @Test
    public void getInterestRate() {
        double interestRate = multi.getInterestRate();
        assertNotNull(interestRate);
        assertEquals(4.7, interestRate);
    }



}
