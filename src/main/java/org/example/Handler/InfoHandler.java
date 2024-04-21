package org.example.Handler;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class InfoHandler {
        OkHttpClient client = new OkHttpClient();
        String apiKey = "f45f935f-7cfc-474b-85a1-98049383f5f4";
        public String infoResult (String symbol){
            String result = "";
            Request request = new Request.Builder()
                    .url("https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=" + symbol)
                    .addHeader("X-CMC_PRO_API_KEY", apiKey)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String jsonData = response.body().string();

                JSONObject jsonResponse = new JSONObject(jsonData);

                if (!jsonResponse.has("data")) {
                    result = "No data found for cryptocurrency with symbol: " + symbol;
                    return result;
                }

                JSONObject data = jsonResponse.getJSONObject("data").getJSONObject(symbol);
                String name = data.getString("name");
                double price = data.getJSONObject("quote").getJSONObject("USD").getDouble("price");
                double change_1h = data.getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_1h");
                double change_24h = data.getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_24h");
                double change_7d = data.getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_7d");

                String pattern = "###.##";
                DecimalFormat formatDecimal = new DecimalFormat(pattern);

                result = "Name: " + name + "\n"
                        + "Price (USD): " + formatDecimal.format(price) + "\n"
                        + "Percent change 1h: " + formatDecimal.format(change_1h) +"%" + "\n"
                        + "Percent change 24h: " + formatDecimal.format(change_24h) +"%"  + "\n"
                        + "Percent change 7d: " + formatDecimal.format(change_7d) +"%"
                ;
            }catch (Exception e) {
                e.printStackTrace();
            }
        return result;
        }
}
