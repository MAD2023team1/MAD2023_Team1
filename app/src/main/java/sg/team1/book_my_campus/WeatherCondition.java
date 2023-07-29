package sg.team1.book_my_campus;

import android.media.Image;

public class WeatherCondition {
    public Double temperature;
    public String Condition;
    public int weatherIcon;


    public Double getTemperature() {
        return temperature;
    }

    public String getCondition() {
        return Condition;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public WeatherCondition(Double temperature, String condition, int weatherIcon) {
        this.temperature = temperature;
        Condition = condition;
        this.weatherIcon = weatherIcon;
    }

    @Override
    public String toString() {
        return "WeatherCondition{" +
                "temperature=" + temperature +
                ", Condition='" + Condition + '\'' +
                ", weatherIcon=" + weatherIcon +
                '}';
    }
}