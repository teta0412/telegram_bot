package org.example;

import org.example.Handler.AIHandler;
import org.example.Handler.InfoHandler;
import org.example.Handler.P2PHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            //Logging
            String user_username = update.getMessage().getChat().getUserName();
            long user_id = update.getMessage().getChatId();
            String msg_text = update.getMessage().getText();

            String answer ="";

            if (msg_text.equals("/start")) {
                SendMessage msg_send = new SendMessage(
                        Long.toString(user_id),
                        "Welcome to Teta Bot !! \n"
                                + "List of commands you can use \n" +
                                "/info : info of a specific cryptocurrency \n" +
                                "/p2p : p2p rate of USDT/VND pair at the moment\n" +
                                "/generate : generate a text to image \n" +
                                "\n" +
                                "-----------------------------------------------\n"+
                                "Developed by https://t.me/teta412 \n" +
                                "Invited link : https://t.me/TetaNewsBot"

                );
                answer = msg_send.getText();
                try {
                    execute(msg_send);
                } catch (Exception e) {
                    return;
                }
            }
            if (msg_text.equals("/p2p")) {
                String result = new P2PHandler().p2pResult();
                SendMessage msg_send = new SendMessage(Long.toString(user_id),result);
                answer = msg_send.getText();
                try {
                    execute(msg_send);
                } catch (Exception e){
                    return;
                }
            }
            if (msg_text.startsWith("/info")) {
                String tmp = msg_text.substring("/info".length()).trim();
                String result = new InfoHandler().infoResult(tmp.toUpperCase().trim());
                SendMessage msg_send = new SendMessage(Long.toString(user_id),result);
                answer = msg_send.getText();
                try {
                    execute(msg_send);
                } catch (Exception e){
                    return;
                }
            }
            if (msg_text.startsWith("/generate")){
                String tmp = msg_text.substring("/generate".length()).trim();
                String result = new AIHandler().generateImage(tmp);
                try {
                    InputStream inputStream = new URL(result).openStream();
                    Path tempFile = Files.createTempFile("temp-photo", ".jpg");
                    Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(user_id);
                    sendPhoto.setPhoto(new InputFile(tempFile.toFile()));
                    sendPhoto.setCaption("Generated Image");
                    try {
                        execute(sendPhoto);
                        Files.deleteIfExists(tempFile);
                    } catch (Exception e){
                        return;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                answer = "Generated Image";
            }

            log(user_username, Long.toString(user_id), msg_text, answer);
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
    private void log(String user_name, String user_id, String txt, String bot_answer) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + user_name + ". (id = " + user_id + ") \n Text - " + txt);
        System.out.println("Bot answer: \n Text - " + bot_answer);
    }

}
