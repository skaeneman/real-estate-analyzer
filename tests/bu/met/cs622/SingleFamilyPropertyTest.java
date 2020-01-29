package bu.met.cs622;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleFamilyPropertyTest {

    SingleFamilyProperty single = new SingleFamilyProperty(4.7, 3000, 300000,
            30, 1200, 15000,
            1200, 6000, 850000,
            90000);

    @Test
    public void propertySquareFootage() {
        String response = single.propertySquareFootage();
        assertEquals("Square feet: 3000.0 \nsingle-family rate: 4.7%", response);
    }

    // test accessor's and mutator's (getters and setters)
    @Test
    public void getInterestRate() {
        double interestRate = single.getInterestRate();
        assertNotNull(interestRate);
        assertEquals(4.7, interestRate);
    }

    @Test
    public void setInterestRate() {
        single.setInterestRate(3.8);
        double interestRate = single.getInterestRate();
        assertEquals(3.8, interestRate);
    }

}