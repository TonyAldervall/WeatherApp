package com.example.weatherapp.controllers;

import com.example.weatherapp.data.Forecast24;
import com.example.weatherapp.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeatherController {
    @Autowired
    WeatherService service;

    @GetMapping("/forecast")
    public String forecast(Model m){
        Forecast24 forecast = service.bestForecast();

        m.addAttribute("forecast", forecast);

        return "forecast";
    }
}
