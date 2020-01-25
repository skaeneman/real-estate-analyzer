import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReturnOneTest {

    @Test
    public void testData() throws Exception {
        ReturnOne testing = new ReturnOne();
        assertEquals(1, testing.testData());
        assertNotEquals(45, testing.testData());
    }

}