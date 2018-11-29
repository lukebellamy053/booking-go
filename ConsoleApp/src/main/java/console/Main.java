package console;

public class Main {

    /**
     * Application entry point
     *
     * @param args - The application arguments
     */
    public static void main(String[] args) {
        try {
            // Get the parameters as a map so we can access them by name
            final ArgumentParser parser = new ArgumentParser(args);
            final String pickup = parser.getArgument("-pickup");
            final String dropoff = parser.getArgument("-dropoff");
            final String passengers = parser.getArgument("-passengers");
            final int pass = Integer.parseInt(passengers);
            new FulfillmentEngine(pickup, dropoff, pass);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            System.out.println("Invalid arguments passed.");
            System.out.println("-pickup {pickup_lat,pickup_lon}");
            System.out.println("-dropoff {dropoff_lat,dropoff_lon}");
            System.out.println("-passengers {passengers}");
        }
    }
}
