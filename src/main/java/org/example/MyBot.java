package org.example;

import org.example.models.WeatherResponse;
import org.example.services.IconService;
import org.example.services.WeatherService;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBot extends TelegramLongPollingBot {

    WeatherService weatherService;
    Map<Long, Location> locationMap;
    IconService iconService;

    public MyBot(WeatherService weatherService, IconService iconService) {
        this.weatherService = weatherService;
        locationMap = new HashMap<>();
        this.iconService = iconService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText();
        Long chatId = update.getMessage().getChatId();

        getWeatherInfo(text, chatId, update);
    }

    @Override
    public String getBotUsername() {
        return "My_Weather2_Bot";
    }

    @Override
    public String getBotToken() {
        return "6313876630:AAH-H_8-iU6ghqwbXfaO5FrD_31EebO2JdY";
    }

    void getLocation(Long chatId){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("\uD83D\uDCCD Share Location");
        keyboardButton.setRequestLocation(true);
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(keyboardButton);
        keyboardRowList.add(keyboardRow);
        keyboardMarkup.setKeyboard(keyboardRowList);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Welcome to weather bot!");
        sendMessage.setReplyMarkup(keyboardMarkup);

        executeMessage(sendMessage);  //execute
    }

    void getWeatherInfo(String text, Long chatId, Update update){

        SendMessage sendMessage = new SendMessage();

        if(update.getMessage().hasLocation() || locationMap.get(chatId) != null){
            Location location = new Location();

            if(update.getMessage().hasLocation()){
                location = update.getMessage().getLocation();
                locationMap.put(chatId, location);
            }
            else if(locationMap.get(chatId) != null){
              location = locationMap.get(chatId);
            }


            WeatherResponse weatherResponse = callWeatherService(location);

            String setTxt = " ---------------------------------- " + "\n" +
                    "\uD83D\uDCCD" + weatherResponse.getSys().getCountry() + "\n" +
                    "\uD83C\uDF21" + weatherResponse.getMain().getTemp() + "\n" +
                    //iconService.getEmoji(weatherResponse.getWeather().get(0).getIcon()) +
                    "\uD83C\uDF24 "+ weatherResponse.getWeather().get(0).getMain() +  "\n" +
                    "\uD83D\uDCA6 humidity: "+weatherResponse.getMain().getHumidity() + "\n" +
                    "\uD83C\uDF2C wind speed: "+weatherResponse.getWind().getSpeed() + "\n" +
                    "\uD83D\uDCAC description: "+weatherResponse.getWeather().get(0).getDescription() + "\n" +
                    "\uD83C\uDF07 sunset: "+weatherResponse.getSys().getSunset() + "\n" +
                    "\uD83C\uDF04 sunrise: "+weatherResponse.getSys().getSunrise() + "\n" +
            " ---------------------------------- ";

            System.out.println(setTxt);

            sendMessage.setText(setTxt);

            sendMessage.setChatId(chatId);

            executeMessage(sendMessage); //execute
        }
        if(text.startsWith("/start")){
            getLocation(chatId);
        }
    }

    private WeatherResponse callWeatherService(Location location){
        WeatherResponse weatherResponse = weatherService
                .getWeather(location.getLongitude(), location.getLatitude());
        return weatherResponse;
    }

    private void executeMessage(SendMessage sendMessage){
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
