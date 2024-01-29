package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) {

//        newsapi = 40caee4d06d344b1a50fb2d90dc1e0fb
                try {
                    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
                    telegramBotsApi.registerBot(new TelegramBot());
//                    telegramBotsApi.registerBot(new HelloBot());

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }


}
