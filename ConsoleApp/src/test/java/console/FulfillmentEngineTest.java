package console;

import org.junit.Test;

import static org.junit.Assert.*;

public class FulfillmentEngineTest {

    @Test
    public void Fulfillment() {
        try {
            // Make sure it runs without failure
            new FulfillmentEngine("123,123", "123,123", 10);
        } catch (InvalidArgumentException e) {
            fail("Exception");
        }
    }

    @Test
    public void DaveEngine() {
        try {
            // Make sure it runs without failure
            new DaveEngine("123,123", "123,123");
        } catch (InvalidArgumentException e) {
            fail("Exception");
        }
    }

}