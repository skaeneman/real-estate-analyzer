package bu.met.cs622;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReturnOneTest {

    @Test
    public void testData() throws Exception {
        ReturnOne testing = new ReturnOne();
        Assertions.assertEquals(1, testing.testData());
        Assertions.assertNotEquals(45, testing.testData());
    }

}