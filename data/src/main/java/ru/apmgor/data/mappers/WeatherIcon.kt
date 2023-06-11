package ru.apmgor.data.mappers

import ru.apmgor.data.R

enum class WeatherIcon {
    f01d,
    f01n,
    f02d,
    f02n,
    f03d,
    f03n,
    f04d,
    f04n,
    f09d,
    f09n,
    f10d,
    f10n,
    f11d,
    f11n,
    f13d,
    f13n,
    f50d,
    f50n;

    fun mapWeatherIconToDrawable(): Int {
        return when(this) {
            f01d -> R.drawable.f01d
            f01n -> R.drawable.f01n
            f02d -> R.drawable.f02d
            f02n -> R.drawable.f02n
            f03d -> R.drawable.f03d
            f03n -> R.drawable.f03n
            f04d -> R.drawable.f04d
            f04n -> R.drawable.f04n
            f09d -> R.drawable.f09d
            f09n -> R.drawable.f09n
            f10d -> R.drawable.f10d
            f10n -> R.drawable.f10n
            f11d -> R.drawable.f11d
            f11n -> R.drawable.f11n
            f13d -> R.drawable.f13d
            f13n -> R.drawable.f13n
            f50d -> R.drawable.f50d
            f50n -> R.drawable.f50n
        }
    }
}

