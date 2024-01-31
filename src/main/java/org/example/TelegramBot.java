package org.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class TelegramBot extends TelegramLongPollingBot{
    private static final String API_KEY = "40caee4d06d344b1a50fb2d90dc1e0fb";
    private static final String BASE_URL = "https://newsapi.org/v2/";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            {
                // Set variables
                String message_text = update.getMessage().getText();
                long chat_id = update.getMessage().getChatId();
                if (message_text.equals("/start")) {
                    // User send /start
                    SendMessage message = new SendMessage(); // Create a message object object
                    message.setChatId(chat_id);
                    message.setText("Click");

                    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

                    List<KeyboardRow> keyboardList = new ArrayList<>();

                    KeyboardRow row = new KeyboardRow();
                    row.add("Hello");
                    row.add("IT News");
                    row.add("Crypto News");
                    keyboardList.add(row);
                    keyboardMarkup.setKeyboard(keyboardList);
                    message.setReplyMarkup(keyboardMarkup);
                    try {
                        execute(message); // Sending our message object to user
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if (message_text.equals("Hello")) {
                    // User sent /pic
                    String f_id = "AgACAgUAAxkBAAMrZbNvSsvf9bnDFVTEN9KWRT-o31AAAiy7MRvUlphVQv5Ot0XM5HABAAMCAAN4AAM0BA";
                    SendPhoto msg = new SendPhoto();
                    msg.setChatId(chat_id);
                    msg.setPhoto(new InputFile(f_id));
                    msg.setCaption("Hello Bro");
                    try {
                        execute(msg); // Call method to send the photo
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if (message_text.equals("IT News")) {
                    String endpoint = "everything";
                    String domain = "";
                    String pageSize="";
                    String url = BASE_URL + endpoint +"?domains=techcrunch.com"+ "&apiKey=" + API_KEY +"&pageSize=5" ;
                    SendMessage message = new SendMessage();
                    try {
                        JSONObject response = makeHttpRequest(url);
                        JSONArray articles = response.getJSONArray("articles");
                        List<String> headlines = new ArrayList<>();
                        for (int i = 0; i < articles.length(); i++) {
                            JSONObject article = articles.getJSONObject(i);
                            String title = article.getString("title");
                            String sources_url = article.getString("url");
                            message.setChatId(chat_id);
                            message.setText(title + "\n" +sources_url);
                            execute(message);
                        }
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Unknown command
                    SendMessage message = new SendMessage();// Create a message object object
                    message.setChatId(chat_id);
                    message.setText("Unknown command");
                    try {
                        execute(message); // Sending our message object to user
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }


    @Override
    public String getBotUsername() {
        return System.getenv("TELE_USERNAME");
    }

    @Override
    public String getBotToken() {
        return System.getenv("TELE_TOKEN");
    }

    private static JSONObject makeHttpRequest(String url) throws IOException, JSONException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return new JSONObject(response.toString());
        } else {
            throw new IOException("HTTP request failed with code: " + responseCode);
        }
    }

}
