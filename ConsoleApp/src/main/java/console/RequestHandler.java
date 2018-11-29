package console;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * A class to send the HTTP requests
 */
class RequestHandler {

    /**
     * Send a get request
     *
     * @param uri - The URL so send the request to
     * @return The JSON Response object
     * @throws IOException Throws an IO error is a timeout or other issue occurs
     */
    static JSONObject sendGet(String uri, Map<String, String> params) throws IOException {
        URL url = new URL(uri + "?" + RequestHandler.paramsToString(params));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(2000);
        return RequestHandler.readConnection(con);
    }

    /**
     * Read a connection request and return a JSON response
     *
     * @param connection - The Connection object to watch
     * @return The JSON response
     * @throws IOException If the response if not valid JSON or the stream is broken
     */
    private static JSONObject readConnection(HttpURLConnection connection) throws IOException {
        // Create a buffered reader from the connection
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        // Use a string builder to construct the result
        StringBuilder content = new StringBuilder();
        // Read all the lines from the input response
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        // Close the buffer
        in.close();
        // Turn the response into a JSON Object
        return new JSONObject(content.toString());
    }

    /**
     * Covert a map to a parameter string
     *
     * @param params - The parameters to convert
     * @return The parameter string
     */
    private static String paramsToString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        // Add each parameter to the request
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }
        // Get the result
        String resultString = result.toString();
        // Remove the last character '&' and return
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

}
