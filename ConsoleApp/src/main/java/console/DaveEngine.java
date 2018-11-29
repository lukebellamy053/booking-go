package console;

/**
 * Engine to handle the dave only section
 */
public class DaveEngine extends FulfillmentEngine {
    /**
     * Start the fulfillment engine
     *
     * @param pickup  - The latitude to search for
     * @param dropoff - The longitude to search for
     */
    DaveEngine(String pickup, String dropoff) throws InvalidArgumentException {
        super(pickup, dropoff);
    }

    /**
     * Print the results to the console
     */
    protected void printResults() {
        for (CarOption option : this.options) {
            System.out.println(option.getCarType() + " - " + option.getPrice());
        }

        if (options.size() == 0) {
            System.out.println("Sorry, no matches were found for that request");
        }
    }
}
