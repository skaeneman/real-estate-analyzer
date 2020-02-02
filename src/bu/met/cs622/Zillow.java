package bu.met.cs622;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Zillow {
    private static HttpURLConnection connection;

    public void getZillowData() throws IOException {

        BufferedReader reader;
        String line;

        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/albums");
            connection = (HttpURLConnection) url.openConnection();

            // setup API connection request
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.printf("status code.... %s", status);

            // if there is a code over 299 it's likely an error
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

            } else {
                // if connection is successful
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
            System.out.println(responseContent.toString());

        }
        catch (IOException e) {
            System.err.printf("Error:... %s", e.getMessage());
            e.printStackTrace();
        }


        System.out.println("This is a test of the new class....");
    }

}
