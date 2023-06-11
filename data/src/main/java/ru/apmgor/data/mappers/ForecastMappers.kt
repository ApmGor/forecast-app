package ru.apmgor.data.mappers

import ru.apmgor.data.R
import ru.apmgor.data.mappers.WindDirection.E
import ru.apmgor.data.mappers.WindDirection.ENE
import ru.apmgor.data.mappers.WindDirection.ESE
import ru.apmgor.data.mappers.WindDirection.N
import ru.apmgor.data.mappers.WindDirection.NE
import ru.apmgor.data.mappers.WindDirection.NNE
import ru.apmgor.data.mappers.WindDirection.NNW
import ru.apmgor.data.mappers.WindDirection.NW
import ru.apmgor.data.mappers.WindDirection.S
import ru.apmgor.data.mappers.WindDirection.SE
import ru.apmgor.data.mappers.WindDirection.SSE
import ru.apmgor.data.mappers.WindDirection.SSW
import ru.apmgor.data.mappers.WindDirection.SW
import ru.apmgor.data.mappers.WindDirection.W
import ru.apmgor.data.mappers.WindDirection.WNW
import ru.apmgor.data.mappers.WindDirection.WSW
import ru.apmgor.data.model.AlertsForecastApi
import ru.apmgor.data.model.AlertsForecastDb
import ru.apmgor.data.model.CurrentForecastDb
import ru.apmgor.data.model.DailyForecastApi
import ru.apmgor.data.model.DailyForecastDb
import ru.apmgor.data.model.ForecastApi
import ru.apmgor.data.model.ForecastDb
import ru.apmgor.data.model.HourlyForecastDb
import ru.apmgor.data.model.MinutelyForecastApi
import ru.apmgor.data.model.MinutelyForecastDb
import ru.apmgor.data.model.UnitsDb
import ru.apmgor.forecast.data.model.HourlyForecastApi
import ru.apmgor.domain.model.AlertsForecast
import ru.apmgor.domain.model.CurrentForecast
import ru.apmgor.domain.model.DailyForecast
import ru.apmgor.domain.model.Forecast
import ru.apmgor.domain.model.HourlyForecast
import ru.apmgor.domain.model.HourlyList
import ru.apmgor.domain.model.MinutelyForecast
import ru.apmgor.domain.model.Sunrise
import ru.apmgor.domain.model.Sunset
import ru.apmgor.domain.model.Weather
import ru.apmgor.domain.model.Wind
import kotlin.math.roundToInt

fun ForecastDb.toForecast(unitsDb: UnitsDb): Forecast {
    val timeZoneOffset = city?.timezoneOffset
    return Forecast(
        city = city?.toCity(),
        currentForecast = current?.toCurrentForecast(unitsDb),
        alertsForecast = alerts.map { it.toAlertsForecast(unitsDb, timeZoneOffset) },
        minutelyForecast = minutely.map { it.toMinutelyForecast(unitsDb) },
        hourlyForecast = hourly.toListHourlyForecast(daily, unitsDb, timeZoneOffset),
        dailyForecast = daily.map { it.toDailyForecast(unitsDb, timeZoneOffset) }
    )
}

fun ForecastApi.toCurrentForecastDb() =
    CurrentForecastDb(
        temp = current.temp,
        feelsLike = current.feels_like,
        pressure = current.pressure,
        humidity = current.humidity,
        dewPoint = current.dew_point,
        uvi = current.uvi,
        visibility = current.visibility,
        windSpeed = current.wind_speed,
        windDeg = current.wind_deg,
        weatherDesc = current.weather[0].description,
        weatherIcon = current.weather[0].icon
    )

fun CurrentForecastDb.toCurrentForecast(unitsDb: UnitsDb): CurrentForecast {
    val tempUnits = unitsDb.toTempUnits()
    val windUnits = unitsDb.toWindUnits()
    val pressureUnits = unitsDb.toPressureUnits()
    val distUnits = unitsDb.toDistUnits()
    val windDirectionIndex = mapWindDegToWindDirectionIndex(windDeg)
    val windDir = enumValues<WindDirection>()[windDirectionIndex]
    return CurrentForecast(
        temp = tempUnits.convertTemp(temp),
        feelsLike = tempUnits.convertTemp(feelsLike),
        pressure = pressureUnits.convertPressure(pressure),
        humidity = humidity,
        dewPoint = tempUnits.convertTemp(dewPoint),
        uvi = uvi,
        visibility = distUnits.convertDistance(visibility),
        wind = Wind(
            windUnits.convertWind(windSpeed),
            mapWindSpeedToWindNameIndex(windSpeed),
            windDirectionIndex,
            windDir.mapWindDirectionToDrawableDay(),
            windDir.mapWindDirectionToDrawableNight()
        ),
        weather = Weather(
            weatherDesc.replaceFirstChar { it.uppercase() },
            WeatherIcon.valueOf("f$weatherIcon").mapWeatherIconToDrawable()
        )
    )
}

fun AlertsForecastApi.toAlertsForecastDb(id: Int) =
    AlertsForecastDb(
        id = id,
        event = event,
        start = start,
        end = end,
        description = description
    )

fun AlertsForecastDb.toAlertsForecast(unitsDb: UnitsDb, timeZoneOffset: Long?): AlertsForecast {
    val timeUnits = unitsDb.toTimeUnits()
    return AlertsForecast(
        event = event,
        start = timeUnits.convertTime(start, timeZoneOffset) + ", "
                + getDayTimeAsString(start, timeZoneOffset) { "dd MMM" },
        end = timeUnits.convertTime(end, timeZoneOffset) + ", "
                + getDayTimeAsString(end, timeZoneOffset) { "dd MMM" },
        description = description.replaceFirstChar { it.uppercase() }
    )
}

fun List<AlertsForecastApi>.toListAlertsForecastDb() =
    filter { Regex("[а-яА-Я]").containsMatchIn(it.event) }
    .mapIndexed { i, el -> el.toAlertsForecastDb(i) }

fun MinutelyForecastApi.toMinutelyForecastDb(id: Int) =
    MinutelyForecastDb(
        id = id,
        precipitation = precipitation
    )

fun List<MinutelyForecastApi>.toListMinutelyForecastDb() =
    mapIndexed { i, el -> el.toMinutelyForecastDb(i) }

fun MinutelyForecastDb.toMinutelyForecast(unitsDb: UnitsDb): MinutelyForecast {
    val precipitationUnits = unitsDb.toPrecipitationUnits()
    return MinutelyForecast(
        precipitation = precipitationUnits.convertPrecipitation(precipitation)
    )
}

fun HourlyForecastApi.toHourlyForecastDb(id: Int) =
    HourlyForecastDb(
        id = id,
        dt = dt,
        temp = temp,
        weatherIcon = weather[0].icon
    )

fun List<HourlyForecastApi>.toListHourlyForecastDb() =
    mapIndexed { i, el -> el.toHourlyForecastDb(i) }

fun List<HourlyForecastDb>.toListHourlyForecast(
    listDaily: List<DailyForecastDb>,
    unitsDb: UnitsDb,
    timeZoneOffset: Long?
) =
    if (this.isEmpty() || listDaily.isEmpty()) {
        emptyList()
    } else {
        val minDt = this.minOf { it.dt }
        val maxDt = this.maxOf { it.dt }
        val listSunrises = listDaily.filter {
            it.sunrise in minDt..maxDt
        }.map { it.toSunrise() }
        val listSunsets = listDaily.filter {
            it.sunset in minDt..maxDt
        }.map { it.toSunset() }
        val result =
        (this.map { it.toHourlyForecast(unitsDb) } + listSunrises + listSunsets)
            .sortedBy { it.dt }
        result.map {
            when(it) {
                is HourlyForecast -> it.toHourlyForecast(unitsDb, timeZoneOffset)
                is Sunset -> it.toSunset(unitsDb, timeZoneOffset)
                is Sunrise -> it.toSunrise(unitsDb, timeZoneOffset)
            }
        }
    }

fun HourlyForecastDb.toHourlyForecast(unitsDb: UnitsDb): HourlyForecast<Long> {
    val tempUnits = unitsDb.toTempUnits()
    return HourlyForecast(
        dt = dt,
        temp = tempUnits.convertTemp(temp),
        weatherIconDay = WeatherIcon.valueOf("f$weatherIcon").mapWeatherIconToDrawable()
    )
}

fun HourlyForecast<Long>.toHourlyForecast(
    unitsDb: UnitsDb,
    timeZoneOffset: Long?
): HourlyForecast<String> {
    return HourlyForecast(
        dt = timeOrDay(this.dt, unitsDb, timeZoneOffset),
        temp = this.temp,
        weatherIconDay = this.weatherIconDay
    )
}

fun DailyForecastDb.toSunset(): HourlyList<Long> {
    return Sunset(
        dt = sunset,
        name = R.string.sunset_desc,
        weatherIconDay = R.drawable.sunset,
        weatherIconNight = R.drawable.sunset_white
    )
}

fun Sunset<Long>.toSunset(
    unitsDb: UnitsDb,
    timeZoneOffset: Long?
): Sunset<String> {
    return Sunset(
        dt = timeOrDay(this.dt, unitsDb, timeZoneOffset),
        name = this.name,
        weatherIconDay = this.weatherIconDay,
        weatherIconNight = this.weatherIconNight
    )
}

fun DailyForecastDb.toSunrise(): HourlyList<Long> {
    return Sunrise(
        dt = sunrise,
        name = R.string.sunrise_desc,
        weatherIconDay = R.drawable.sunrise,
        weatherIconNight = R.drawable.sunrise_white
    )
}

fun Sunrise<Long>.toSunrise(
    unitsDb: UnitsDb,
    timeZoneOffset: Long?
): Sunrise<String> {
    return Sunrise(
        dt = timeOrDay(this.dt, unitsDb, timeZoneOffset),
        name = this.name,
        weatherIconDay = this.weatherIconDay,
        weatherIconNight = this.weatherIconNight
    )
}

private fun timeOrDay(
    dt: Long,
    unitsDb: UnitsDb,
    timeZoneOffset: Long?
): String {
    val timeUnits = unitsDb.toTimeUnits()
    val time = timeUnits.convertTime(dt, timeZoneOffset)
    val isNewDay = time == "12:00AM" || time == "00:00"
    return if (isNewDay) getDayTimeAsString(dt, timeZoneOffset) { "dd MMM" } else time
}

fun DailyForecastApi.toDailyForecastDb(id: Int) =
    DailyForecastDb(
        id = id,
        dt = dt,
        weatherDesc = weather[0].description,
        weatherIcon = weather[0].icon,
        tempDay = temp.day,
        tempNight = temp.night,
        rain = rain,
        pop = pop,
        windSpeed = wind_speed,
        windDeg = wind_deg,
        pressure = pressure,
        humidity = humidity,
        uvi = uvi,
        sunrise = sunrise,
        sunset = sunset
    )

fun List<DailyForecastApi>.toListDailyForecastDb() =
    mapIndexed { i, el -> el.toDailyForecastDb(i) }

fun DailyForecastDb.toDailyForecast(unitsDb: UnitsDb, timeZoneOffset: Long?): DailyForecast {
    val timeUnits = unitsDb.toTimeUnits()
    val pressureUnits = unitsDb.toPressureUnits()
    val windUnits = unitsDb.toWindUnits()
    val windDirectionIndex = mapWindDegToWindDirectionIndex(windDeg)
    val windDir = enumValues<WindDirection>()[windDirectionIndex]
    return DailyForecast(
        dt = getDayTimeAsString(dt, timeZoneOffset) { "EEE dd MMM" },
        weather = Weather(
            weatherDesc.replaceFirstChar { it.uppercase() },
            WeatherIcon.valueOf("f$weatherIcon").mapWeatherIconToDrawable()
        ),
        tempDay = tempDay.roundToInt(),
        tempNight = tempNight.roundToInt(),
        rain = rain,
        pop = (pop * 100).roundToInt(),
        wind = Wind(
            windUnits.convertWind(windSpeed),
            mapWindSpeedToWindNameIndex(windSpeed),
            windDirectionIndex,
            windDir.mapWindDirectionToDrawableDay(),
            windDir.mapWindDirectionToDrawableNight()
        ),
        pressure = pressureUnits.convertPressure(pressure),
        humidity = humidity,
        uvi = uvi,
        sunrise = timeUnits.convertTime(sunrise, timeZoneOffset),
        sunset = timeUnits.convertTime(sunset, timeZoneOffset)
    )
}


private fun mapWindSpeedToWindNameIndex(windSpeed: Double) = when(windSpeed) {
    in (0.00..0.49) -> 0
    in (0.50..1.99) -> 1
    in (2.00..3.49) -> 2
    in (3.50..5.49) -> 3
    in (5.50..8.49) -> 4
    in (8.50..10.99) -> 5
    in (11.00..13.99) -> 6
    in (14.00..16.99) -> 7
    in (17.00..20.49) -> 8
    in (20.50..23.99) -> 9
    in (24.00..27.99) -> 10
    in (28.00..31.99) -> 11
    else -> 12
}

private fun mapWindDegToWindDirectionIndex(windDeg: Int) = when(windDeg) {
    in (0..11) -> N.ordinal
    in (12..33) -> NNE.ordinal
    in (34..56) -> NE.ordinal
    in (57..78) -> ENE.ordinal
    in (79..101) -> E.ordinal
    in (102..123) -> ESE.ordinal
    in (124..146) -> SE.ordinal
    in (147..168) -> SSE.ordinal
    in (169..191) -> S.ordinal
    in (192..213) -> SSW.ordinal
    in (214..236) -> SW.ordinal
    in (237..258) -> WSW.ordinal
    in (259..281) -> W.ordinal
    in (282..303) -> WNW.ordinal
    in (304..326) -> NW.ordinal
    in (327..348) -> NNW.ordinal
    else -> N.ordinal
}
