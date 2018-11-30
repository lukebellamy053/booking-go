package console;

import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * A class to perform the search on the latitude and longitudes required
 */
class FulfillmentEngine {

    /**
     * Holds all of the suppliers to use
     */
    private String[] suppliers = new String[]{
            "https://techtest.rideways.com/dave",
            "https://techtest.rideways.com/eric",
            "https://techtest.rideways.com/jeff"
    };

    /**
     * The found options
     */
    List<CarOption> options = new ArrayList<CarOption>();

    /**
     * Start the fulfillment engine
     *
     * @param pickup     - The latitude to search for
     * @param dropoff    - The longitude to search for
     * @param passengers - The number of passengers to have
     */
    FulfillmentEngine(String pickup, String dropoff, int passengers) throws InvalidArgumentException {
        if (pickup == null || dropoff == null) {
            throw new InvalidArgumentException("Parameters cannot be null");
        }
        this.init();
        this.start(pickup, dropoff, passengers);
        this.filter();
        this.sort();
        this.printResults();
    }

    /**
     * Only use the dave supplier for part 1
     *
     * @param pickup  - The pickup location
     * @param dropoff - The dropoff location
     * @throws InvalidArgumentException If either param is null
     */
    FulfillmentEngine(String pickup, String dropoff) throws InvalidArgumentException {
        if (pickup == null || dropoff == null) {
            throw new InvalidArgumentException("Parameters cannot be null");
        }
        this.suppliers = new String[]{"https://techtest.rideways.com/dave"};
        this.init();
        this.start(pickup, dropoff, 0);
        this.sort();
        this.printResults();
    }

    /**
     * Set the required variables
     */
    private void init() {
        Map<String, Integer> sizes = new HashMap<String, Integer>();
        sizes.put("STANDARD", 4);
        sizes.put("EXECUTIVE", 4);
        sizes.put("LUXURY", 4);
        sizes.put("PEOPLE_CARRIER", 6);
        sizes.put("LUXURY_PEOPLE_CARRIER", 6);
        sizes.put("MINIBUS", 16);
        CarOption.setCarSizes(sizes);
    }

    /**
     * Start the search
     *
     * @param pickup     - The pickup location
     * @param dropoff    - The dropoff location
     * @param passengers - The number of passengers
     */
    private void start(String pickup, String dropoff, int passengers) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("pickup", pickup);
        params.put("dropoff", dropoff);

        JSONObject response;
        for (String supplier : this.suppliers) {
            try {
                response = RequestHandler.sendGet(supplier, params);
                this.options.addAll(CarOption.getResults(response, passengers));
            } catch (IOException e) {
                // Do nothing, ignore the results
            }
        }
    }

    /**
     * Filter out duplicate car types
     */
    private void filter() {
        Map<String, CarOption> filteredOptions = new HashMap<String, CarOption>();
        String carType;
        CarOption compareOption;
        boolean canAdd;
        // For all of the options, check if it can be added
        for (CarOption option : this.options) {
            canAdd = true;
            carType = option.getCarType();
            // Check if the car has been added already
            if (filteredOptions.containsKey(carType)) {
                compareOption = filteredOptions.get(carType);
                if (compareOption != null) {
                    // Check to see which is cheaper
                    canAdd = option.getPrice() < compareOption.getPrice();
                }
            }
            // Check if we can set this option as the new value
            if (canAdd) {
                filteredOptions.put(carType, option);
            }
        }
        // Set the new filtered options
        this.options = new ArrayList<CarOption>(filteredOptions.values());
    }

    /**
     * Sort the cars into descending price order
     */
    private void sort() {
        // Sort the options into descending order
        Collections.sort(this.options, new Comparator<CarOption>() {
            public int compare(CarOption o1, CarOption o2) {
                if (o1.getPrice() > o2.getPrice()) return -1;
                if (o1.getPrice() < o2.getPrice()) return 1;
                return 0;
            }
        });
    }

    /**
     * Print the results to the console
     */
    protected void printResults() {
        for (CarOption option : this.options) {
            System.out.println(option.getCarType() + " - " + option.getSupplier() + " - " + option.getPrice());
        }

        if (options.size() == 0) {
            System.out.println("Sorry, no matches were found for that request");
        }
    }
}
