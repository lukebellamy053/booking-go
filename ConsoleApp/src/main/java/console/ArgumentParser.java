package console;

import java.util.HashMap;
import java.util.Map;

/**
 * Parses an array of string arguments to a map
 */
class ArgumentParser {

    /**
     * Holds the extracted arguments
     */
    private final Map<String, String> mArgs = new HashMap<String, String>();

    /**
     * Get the arguments from the array and parse them to a map
     *
     * @param args - The array of arguments
     * @throws InvalidArgumentException - If the arguments are wrong
     */
    ArgumentParser(String[] args) throws InvalidArgumentException {
        String activeKey = null;
        for (final String arg : args) {
            // Check for the indicator that it's a key
            if (arg.charAt(0) == '-') {
                // Make sure there's something after it
                if (arg.length() < 2) {
                    throw new InvalidArgumentException("Argument is not valid " + arg);
                }
                // Set the active key
                activeKey = arg;
                mArgs.put(activeKey, null);
            } else if (activeKey != null && this.mArgs.get(activeKey) == null) {
                // Ignore any trailing parameters
                this.mArgs.put(activeKey, arg);
            } else {
                // Something isn't right
                throw new InvalidArgumentException("Parameter usage is not correct");
            }
        }
    }

    /**
     * Get the individual argument
     *
     * @param key - The name of the parameter
     * @return The argument value
     */
    String getArgument(String key) {
        return this.mArgs.get(key);
    }
}
