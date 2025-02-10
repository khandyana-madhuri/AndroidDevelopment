package com.example.androiddevelopment.data.model

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather> // giving list because sometimes, the temp may be cloudy and rain..
)

data class Main(
    val temp: Float,
    val humidity: Int
)

data class Weather(
    val description: String
)