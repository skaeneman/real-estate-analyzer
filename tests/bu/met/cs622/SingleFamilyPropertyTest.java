package bu.met.cs622;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleFamilyPropertyTest {

    @Test
    void display() {
    }

    @Test
    void singleFamilyMortgage() {
        SingleFamilyProperty investment = new SingleFamilyProperty();
        double mortgage = investment.mortgagePayment(250000, 30, 5);
        assertEquals(1342.0540575303496, mortgage);
    }


}