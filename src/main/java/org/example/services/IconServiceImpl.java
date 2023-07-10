package org.example.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.models.WeatherResponse;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class IconServiceImpl implements IconService{
    String pathFile = "D:\\TelegramBot\\Bot_Java\\Weather_Bot_Practice" +
            "\\src\\main\\resources\\icons.json";

    @Override
    public String getEmoji(String iconCode) {
        Map<String, String> iconsMap = new HashMap<>();
        File file = new File(pathFile);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }

            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Gson gson = new Gson();

            iconsMap = gson.fromJson(stringBuilder.toString(), type);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return iconsMap.get(iconCode);
    }
}
