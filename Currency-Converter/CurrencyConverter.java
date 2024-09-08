
import java.io.*;
import java.net.*;
import java.util.Scanner;
import org.json.JSONObject;

public class CurrencyConverter {
    private static final String API_KEY = "f29506a485074a01a5f03db5";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/";

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        
        System.out.println("Enter the base currency (e.g., USD): ");
        String baseCurrency = s.nextLine().toUpperCase();

        System.out.println("Enter the target currency (e.g., EUR): ");
        String targetCurrency = s.nextLine().toUpperCase();

        
        System.out.println("Enter the amount you want to convert: ");
        double amount = s.nextDouble();

        try {
            
            double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);

            
            double convertedAmount = amount * exchangeRate;

            
            System.out.printf("%.2f %s is equivalent to %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        s.close();
    }

   
    private static double fetchExchangeRate(String baseCurrency, String targetCurrency) throws Exception {
        String urlStr = API_URL + baseCurrency + "/" + targetCurrency;
        @SuppressWarnings("deprecation")
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Failed to fetch exchange rate: HTTP error code : " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.getDouble("conversion_rate");
    }
}


