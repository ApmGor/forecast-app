package ru.apmgor.data.mappers

import ru.apmgor.data.model.CityApi
import ru.apmgor.data.model.CityDb
import ru.apmgor.domain.model.City
import ru.apmgor.domain.model.Coordinates

fun CityApi.toCityDb() =
    CityDb(
        name = local_names?.ru ?: name,
        country = country,
        state = state,
        lat = lat,
        lon = lon
    )

fun CityApi.toCity() =
    City(
        name = local_names?.ru ?: name,
        country = country,
        state = state,
        coordinates = Coordinates(latitude = lat, longitude = lon)
    )

fun CityDb.toCity() =
    City(
        name = name,
        country = country,
        state = state,
        coordinates = Coordinates(latitude = lat, longitude = lon),
        timezoneOffset = timezoneOffset
    )

fun City.toCityDb() =
    CityDb(
        name = name,
        country = country,
        state = state,
        lat = coordinates.latitude,
        lon = coordinates.longitude,
        timezoneOffset = timezoneOffset
    )