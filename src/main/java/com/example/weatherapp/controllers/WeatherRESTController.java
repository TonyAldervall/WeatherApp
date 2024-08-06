package com.example.weatherapp.controllers;

import com.example.weatherapp.data.Forecast24;
import com.example.weatherapp.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherRESTController {
    @Autowired
    WeatherService service;

    @GetMapping("/rs/forecast")
    public Forecast24 forecast(){
        return service.bestForecast();
    }
}
