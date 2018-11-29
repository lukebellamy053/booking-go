package console;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Holds the response from the API
 */
class CarOption {
    // Holds the capacity for cars
    private static Map<String, Integer> mCarSizes;
    // The supplier of this car
    private String mSupplier;
    // The type of car
    private String mCarType;
    // The price of the car
    private int mPrice;


    /**
     * Set the car sizes
     *
     * @param sizes - The size of the cars
     */
    static void setCarSizes(Map<String, Integer> sizes) {
        CarOption.mCarSizes = sizes;
    }

    /**
     * Get a list of the results from a response
     *
     * @param response          - The JSON object from the server
     * @param passengers_filter - The number of people required
     * @return A list of possible options
     */
    static List<CarOption> getResults(JSONObject response, int passengers_filter) {
        String supplier = response.getString("supplier_id");
        JSONArray options = response.getJSONArray("options");
        JSONObject option;
        CarOption result;
        List<CarOption> results = new ArrayList<CarOption>();
        Integer size;
        for (int i = 0; i < options.length(); i++) {
            option = options.getJSONObject(i);
            option.put("supplier_id", supplier);
            result = new CarOption(option);
            size = CarOption.mCarSizes.get(result.getCarType());
            if (size > passengers_filter) {
                results.add(result);
            }
        }
        return results;
    }

    /**
     * Get the supplier for the offer
     *
     * @return The supplier name
     */
    String getSupplier() {
        return this.mSupplier;
    }

    /**
     * Get the car type
     *
     * @return The car type
     */
    String getCarType() {
        return this.mCarType;
    }

    /**
     * Get the price
     *
     * @return The price
     */
    int getPrice() {
        return this.mPrice;
    }

    /**
     * Create an CarOption
     *
     * @param obj The JSON Object to use
     */
    private CarOption(JSONObject obj) {
        int price = obj.getInt("price");
        this.mSupplier = obj.getString("supplier_id");
        this.mCarType = obj.getString("car_type");
        this.mPrice = price;
    }

}
