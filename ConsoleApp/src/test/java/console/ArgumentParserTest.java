package console;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArgumentParserTest {

    /**
     * Test that the parser works under the correct conditions
     */
    @Test
    public void getArguments() {
        String[] args = new String[]{"-a", "123", "-b", "567"};
        try {
            final ArgumentParser parser = new ArgumentParser(args);
            assertEquals("Correct argument is returned", "123", parser.getArgument("-a"));
            assertEquals("Correct argument is returned", "567", parser.getArgument("-b"));
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            fail("An exception occurred");
        }
    }

    /**
     * Test that the parser throws exceptions under the wrong conditions
     */
    @Test
    public void badArgs() {
        String[] args = new String[]{"-a", "123", "-b", "567", "123"};
        try {
            final ArgumentParser parser = new ArgumentParser(args);
        } catch (InvalidArgumentException e) {
            assertNotNull(e);
        }
    }

}