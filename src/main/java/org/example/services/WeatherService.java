package org.example.services;

import org.example.models.WeatherResponse;

public interface WeatherService {
    WeatherResponse getWeather(double lon, double lat);
}
