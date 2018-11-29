package console;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CarOptionTest {

    private static String resultString;
    private static JSONObject response;

    @Before
    public void beforeAllTestMethods() {
        CarOptionTest.resultString = "{\"supplier_id\":\"DAVE\",\"pickup\":\"3.410632,-2.157533\",\"dropoff\":\"3.410632,-2.157533\",\"options\":[{\"car_type\":\"STANDARD\",\"price\":293216},{\"car_type\":\"PEOPLE_CARRIER\",\"price\":564286}]}";
        CarOptionTest.response = new JSONObject(CarOptionTest.resultString);

        Map<String, Integer> sizes = new HashMap<String, Integer>();
        sizes.put("STANDARD", 4);
        sizes.put("EXECUTIVE", 4);
        sizes.put("LUXURY", 4);
        sizes.put("PEOPLE_CARRIER", 6);
        sizes.put("LUXURY_PEOPLE_CARRIER", 6);
        sizes.put("MINIBUS", 16);
        CarOption.setCarSizes(sizes);
    }

    @Test
    public void setCarSizes() {
        Map<String, Integer> sizes = new HashMap<String, Integer>();
        sizes.put("abc", 1);
        sizes.put("aaa", 4);
        CarOption.setCarSizes(sizes);
        Map<String, Integer> testSizes = CarOption.getCarSizes();
        assertEquals("Car size is correct", (Integer) 1, testSizes.get("abc"));
    }

    @Test
    public void getResults() {
        List<CarOption> options = CarOption.getResults(CarOptionTest.response, 0);
        assertTrue(options.size() > 0);
    }

    @Test
    public void getSupplier() {
        List<CarOption> options = CarOption.getResults(CarOptionTest.response, 0);
        assertTrue(options.size() > 0);
        CarOption option = options.get(0);
        String supplier = option.getSupplier();
        assertNotNull(supplier);
    }

    @Test
    public void getCarType() {
        List<CarOption> options = CarOption.getResults(CarOptionTest.response, 0);
        assertTrue(options.size() > 0);
        CarOption option = options.get(0);
        String car = option.getCarType();
        assertNotNull(car);
    }

    @Test
    public void getPrice() {
        List<CarOption> options = CarOption.getResults(CarOptionTest.response, 0);
        assertTrue(options.size() > 0);
        CarOption option = options.get(0);
        int price = option.getPrice();
        assertTrue(price > 0);
    }
}