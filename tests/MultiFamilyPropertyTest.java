import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiFamilyPropertyTest {

    @Test
    void multiFamilyMortgage() {
        MultiFamilyProperty investment = new MultiFamilyProperty();
        double mortgage = investment.mortgagePayment(100000, 30, 5);
        assertEquals(536.8216230121399, mortgage);
    }

}