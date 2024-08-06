package com.example.weatherapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherAppApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(WeatherAppApplication.class, args);
        Runner runner = new Runner();
        runner.run(args);
    }

}
