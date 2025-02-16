import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
public class HttpJsonExample {
   public static void main(String[] args) {
        String url = "https://api.example.com/data"; // Replace with your API endpoint
        try {
            String response = sendGetRequest(url);
            JSONObject jsonResponse = new JSONObject(response);
            System.out.println("Parsed JSON Response:");
            System.out.println(jsonResponse.toString(2)); // Pretty-print JSON
            if (jsonResponse.has("key")) {
                String value = jsonResponse.getString("key");
                System.out.println("Value of 'key': " + value);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String sendGetRequest(String url) throws Exception {
        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();
        try {
            URL apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                throw new RuntimeException("HTTP GET request failed with response code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response.toString();
    }
}