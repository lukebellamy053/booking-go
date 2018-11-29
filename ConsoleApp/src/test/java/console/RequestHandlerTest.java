package console;

import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RequestHandlerTest {

    @Test
    public void sendGet() {
        String url = "https://techtest.rideways.com/dave/";
        Map<String, String> params = new HashMap<String, String>();
        String latLon = "3.410632,-2.157533";
        params.put("pickup", latLon);
        params.put("dropoff", latLon);
        try {
            JSONObject response = RequestHandler.sendGet(url, params);
            assertNotNull("Response is not null", response);
            assertEquals("Supplier is DAVE", "DAVE", response.getString("supplier_id"));
            assertEquals("Pickup is the same as the one sent", latLon, response.getString("pickup"));
            assertEquals("Dropoff is the same as the one sent", latLon, response.getString("dropoff"));
        } catch (IOException e) {
            e.printStackTrace();
            fail("An exception occurred");
        }
    }
}