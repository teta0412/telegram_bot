package org.example.Handler;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;

public class AIHandler {
    String api_key = "lmwr_sk_CVF64XugZg_jTsnlr53K8tm213ZKz3Np7U1ymKHjMLobqsDn";
    String apiUrl = "https://api.limewire.com/api/image/generation";

    OkHttpClient client = new OkHttpClient();

    public String generateImage(String prompt) {
        String result = "";
        var httpClient = HttpClient.newBuilder().build();

        // Create a JSONObject to hold the payload
        JSONObject payload = new JSONObject();

        // Add prompt and aspect_ratio to the payload
        try {
            payload.put("prompt", prompt);
            payload.put("aspect_ratio", "1:1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .uri(URI.create(apiUrl ))
                .header("Content-Type", "application/json")
                .header("X-Api-Version", "v1")
                .header("Accept", "application/json")
                .header("Authorization","Bearer "+api_key)
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String jsonData = response.body();
        try {
            JSONObject jsonResponse = new JSONObject(jsonData);
            JSONArray dataArray = jsonResponse.getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);

                // Extract the "asset_url"
                String assetUrl = dataObject.getString("asset_url");
                result = assetUrl;
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

