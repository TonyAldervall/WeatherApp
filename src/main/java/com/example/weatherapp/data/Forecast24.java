package com.example.weatherapp.data;

public class Forecast24 {
    private String origin;
    private double temp;
    private double humidity;

    public Forecast24(String origin, double temp, double humidity) {
        this.origin = origin;
        this.temp = temp;
        this.humidity = humidity;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Optimal väderprognos för Liljeholmen från " + getOrigin()
                + "\nTemperatur: " + getTemp() + " °C"
                + "\nRelativ luftfuktighet: " + getHumidity() + "%";
    }
    public String toHtmlString(){
        return toString().replace("\n", "<br>");
    }
}
