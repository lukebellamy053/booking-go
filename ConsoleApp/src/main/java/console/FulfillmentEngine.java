package console;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * A class to perform the search on the latitude and longitudes required
 */
class FulfillmentEngine {

    private final String[] suppliers = new String[]{
            "https://techtest.rideways.com/dave",
            "https://techtest.rideways.com/eric",
            "https://techtest.rideways.com/jeff"
    };

    private List<CarOption> options = new ArrayList<CarOption>();

    /**
     * Start the fulfillment engine
     *
     * @param pickup     - The latitude to search for
     * @param dropoff    - The longitude to search for
     * @param passengers - The number of passengers to have
     */
    FulfillmentEngine(String pickup, String dropoff, int passengers) {
        this.init();
        this.start(pickup, dropoff, passengers);
        this.filter();
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
            } catch (SocketTimeoutException e) {
                System.out.println("Ignoring results from " + supplier);
            } catch (IOException e) {
                System.out.println("An error occurred while getting results from " + supplier);
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
        for (CarOption option : this.options) {
            canAdd = true;
            carType = option.getCarType();
            if (filteredOptions.containsKey(carType)) {
                compareOption = filteredOptions.get(carType);
                if (compareOption != null) {
                    canAdd = option.getPrice() < compareOption.getPrice();
                }
            }

            if (canAdd) {
                filteredOptions.put(carType, option);
            }
        }
        this.options = new ArrayList<CarOption>(filteredOptions.values());
    }

    private void sort() {
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
    private void printResults() {
        for (CarOption option : this.options) {
            System.out.println(option.getCarType() + " - " + option.getSupplier() + " - " + option.getPrice());
        }

        if (options.size() == 0) {
            System.out.println("Sorry, no matches were found for that request");
        }
    }
}
