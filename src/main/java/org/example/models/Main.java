package org.example.models;

public class Main {
    double temp;
    double feels_like;
    double temp_min;
    double temp_max;
    double pressure;
    double humidity;
    double sea_level;

    public Main(double temp, double feels_like, double temp_min,
                double temp_max, double pressure, double humidity, double sea_level) {
        this.temp = temp;
        this.feels_like = feels_like;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.humidity = humidity;
        this.sea_level = sea_level;
    }

    public double getTemp() {
        return temp;
    }

    public double getFeels_like() {
        return feels_like;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getSea_level() {
        return sea_level;
    }
}
