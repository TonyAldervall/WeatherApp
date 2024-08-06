package com.example.weatherapp.services;

import com.example.weatherapp.data.Forecast24;
import com.example.weatherapp.data.met.Met;
import com.example.weatherapp.data.meteo.Meteo;
import com.example.weatherapp.data.smhi.Smhi;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalTime;

@Service
public class WeatherService {
    private WebClient client = WebClient.create();
    public Smhi getSmhiData(){

        Mono<Smhi> m = client
                .get()
                .uri("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0300/lat/59.3110/data.json")
                .retrieve()
                .bodyToMono(Smhi.class);

        return m.block();
    }
    public Met getMetData(){
        Mono<Met> m = client
                .get()
                .uri("https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=59.3110&lon=18.0300")
                .retrieve()
                .bodyToMono(Met.class);

        return m.block();
    }
    public Meteo getMeteoData(){
        Mono<Meteo> m = client
                .get()
                .uri("https://api.open-meteo.com/v1/forecast?latitude=59.3094&longitude=18.0234&hourly=temperature_2m,relative_humidity_2m")
                .retrieve()
                .bodyToMono(Meteo.class);

        return m.block();
    }

    public Forecast24 bestForecast(){
        Smhi smhi = getSmhiData();
        Met met = getMetData();
        Meteo meteo = getMeteoData();

        return closestToIdeal(smhi, met, meteo);
    }
    public Forecast24 closestToIdeal(Smhi smhi, Met met, Meteo meteo){
        double smhiTemp = smhi.getTimeSeries().get(24).getParameters().get(10).getValues().get(0);
        double smhiHumidity = smhi.getTimeSeries().get(24).getParameters().get(15).getValues().get(0);

        double metTemp = met.getProperties().getTimeseries().get(24).getData().getInstant().getDetails().getAirTemperature();
        double metHumidity = met.getProperties().getTimeseries().get(24).getData().getInstant().getDetails().getRelativeHumidity();

        double meteoTemp = meteo.getHourly().getTemperature2m().get(LocalTime.now().getHour() + 24);
        double meteoHumidity = meteo.getHourly().getRelativeHumidity2m().get(LocalTime.now().getHour() + 24);

        double smhiDistance = calculateDistance(smhiTemp, smhiHumidity);
        double metDistance = calculateDistance(metTemp,metHumidity);
        double meteoDistance = calculateDistance(meteoTemp, meteoHumidity);

        if (smhiDistance <= metDistance && smhiDistance <= meteoDistance) {
            return new Forecast24("SMHI", smhiTemp, smhiHumidity);
        } else if (metDistance <= smhiDistance && metDistance <= meteoDistance) {
            return new Forecast24("Met", metTemp, metHumidity);
        } else {
            return new Forecast24("Meteo", meteoTemp, meteoHumidity);
        }

    }

    //Närmast 50% fuktighet och närmast 25 grader varmt = optimalt väder. Med mer vikt på temperatur än fuktighet.
    public double calculateDistance(double temp, double humidity){
        double tempWeight = 3;
        double tempDifference = temp - 25;
        double humidityDifference = humidity - 50;

        return Math.sqrt((tempDifference * tempWeight) * (tempDifference * tempWeight) + humidityDifference * humidityDifference);
    }
}