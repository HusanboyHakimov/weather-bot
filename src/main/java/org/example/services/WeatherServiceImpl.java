package org.example.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.models.WeatherResponse;

import java.io.*;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;

public class WeatherServiceImpl implements WeatherService{
    @Override
    public WeatherResponse getWeather(double lon, double lat) {
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather" +
                    "?lat="+ lat +
                    "&lon="+ lon +
                    "&units=metric" +
                    "&appid=53098b92eb8c39562e3c3c3577f7ae1f");
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String row;
            StringBuilder stringBuilder = new StringBuilder();
            while ((row=bufferedReader.readLine())!=null){
                stringBuilder.append(row);
            }

            Type typeToken = new TypeToken<WeatherResponse>(){}.getType();
            Gson gson = new Gson();
            WeatherResponse weatherResponse = gson.fromJson(stringBuilder.toString(), typeToken);
            System.out.println("Country - " + weatherResponse.getSys().getCountry());

            return weatherResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
