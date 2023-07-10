package org.example;

import org.example.services.IconServiceImpl;
import org.example.services.WeatherServiceImpl;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyBot(new WeatherServiceImpl(), new IconServiceImpl()));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}