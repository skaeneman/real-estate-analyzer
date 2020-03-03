package bu.met.cs622;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class InterestRateTest {

    @Test
    void calculateMortgagePoints() {
        InterestRate thread1 = new InterestRate();
        Thread thread2 = new Thread(thread1);

        thread1.start();
        thread2.start();

        assertEquals(1, Thread.currentThread().getId());
    }

    @Test
    void calculateMortgageInterestRate() {
        InterestRate thread1 = new InterestRate();
        Thread thread2 = new Thread(thread1);

        thread1.start();
        thread2.start();

        assertEquals(1, Thread.currentThread().getId());
        assertEquals("3.0", thread1.calculateMortgagePoints(3));
    }

}