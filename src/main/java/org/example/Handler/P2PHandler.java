package org.example.Handler;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class P2PHandler {
    OkHttpClient client = new OkHttpClient();
    public String p2pResult(){
        String result = "";
        Request request = new Request.Builder()
                .url("https://api.coingecko.com/api/v3/simple/price?ids=tether&vs_currencies=vnd")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();

            JSONObject jsonResponse = new JSONObject(jsonData);

            double usdtToVndRate = jsonResponse.getJSONObject("tether").getDouble("vnd");

            String pattern = "###.##";
            DecimalFormat formatDecimal = new DecimalFormat(pattern);

            result = "USDT/VND Exchange Rate: " + formatDecimal.format(usdtToVndRate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}






