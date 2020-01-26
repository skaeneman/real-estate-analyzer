package bu.met.cs622;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvestmentPropertyTest {

    // instantiate MultiFamilyProperty object to test abstract class methods
    MultiFamilyProperty multiFam = new MultiFamilyProperty();

    // instantiate SingleFamilyProperty object to test abstract class methods
    SingleFamilyProperty singleFam = new SingleFamilyProperty();

    @Test
    void mortgagePayment() {
        double mortgage = multiFam.mortgagePayment(100000, 30, 5);
        assertEquals(536.8216230121399, mortgage);
    }

    @Test
    void netOperatingIncome() {
        double noi = singleFam.netOperatingIncome(90000, 12000,15000,
                1200, 6000);

        assertEquals(55800, noi);
    }
}