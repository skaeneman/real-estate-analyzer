package bu.met.cs622;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class YelpAPI {

    private static HttpURLConnection connection;
    String apiKey = "J52kztJ0A-Ylge8QDPDooqbBbchhtxQfi5hjigcFFowq61CIjWJlmxkh0o03blry5Xw8pSTcMcWAVCwVD1XliCisuvoeA7JXP_LRWYKWJTNGC6LnKXJTvbvfXRU3XnYx";
    String apiBlankSpace = " "; // blank space needed after "Bearer" and before the API key

    /**
     * Connects to the Yelp Fusion API to serach business reviews and locations
     * @param city      the city the business is located in
     * @param state     the state the business is located in
     * @return          data about businesses in the selected city\state in json format
     */
    public String getYelpData(String city, String state) throws IOException {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("https://api.yelp.com/v3/businesses/search?location="+city+","+state);

            connection = (HttpURLConnection) url.openConnection();

            // setup API connection request
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Authorization", "Bearer" + apiBlankSpace + apiKey);

            int status = connection.getResponseCode(); // get the http response code
//            System.out.printf("status code.... %s", status);

            // if there is a code over 299 it's likely an error
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            } else {
                // if the connection is a successful 200 code
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
//            System.out.println(responseContent.toString());
        }
        catch (IOException e) {
            System.err.printf("Error:... %s", e.getMessage());
            e.printStackTrace();
        }
        return responseContent.toString();
    }

}
