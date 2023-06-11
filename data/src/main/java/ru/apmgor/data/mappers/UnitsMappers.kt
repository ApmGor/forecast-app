package ru.apmgor.data.mappers

import ru.apmgor.data.model.UnitsDb
import ru.apmgor.domain.model.DistUnits
import ru.apmgor.domain.model.DistUnits.KM
import ru.apmgor.domain.model.DistUnits.MI
import ru.apmgor.domain.model.PrecipitationUnits
import ru.apmgor.domain.model.PrecipitationUnits.INCH
import ru.apmgor.domain.model.PrecipitationUnits.MM
import ru.apmgor.domain.model.PressureUnits
import ru.apmgor.domain.model.PressureUnits.GPA
import ru.apmgor.domain.model.PressureUnits.MMHG
import ru.apmgor.domain.model.TempUnits
import ru.apmgor.domain.model.TempUnits.C
import ru.apmgor.domain.model.TempUnits.F
import ru.apmgor.domain.model.TimeUnits
import ru.apmgor.domain.model.TimeUnits.H12
import ru.apmgor.domain.model.TimeUnits.H24
import ru.apmgor.domain.model.Units
import ru.apmgor.domain.model.WindUnits
import ru.apmgor.domain.model.WindUnits.KMH
import ru.apmgor.domain.model.WindUnits.MIH
import ru.apmgor.domain.model.WindUnits.MS
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

fun UnitsDb.toTempUnits() =
    TempUnits.values()[tempUnits]

fun UnitsDb.toWindUnits() =
    WindUnits.values()[windUnits]

fun UnitsDb.toPressureUnits() =
    PressureUnits.values()[pressureUnits]

fun UnitsDb.toPrecipitationUnits() =
    PrecipitationUnits.values()[precipitationUnits]

fun UnitsDb.toDistUnits() =
    DistUnits.values()[distUnits]

fun UnitsDb.toTimeUnits() =
    TimeUnits.values()[timeUnits]

fun UnitsDb.toListUnit() =
    listOf<Units>(
        toTempUnits(),
        toWindUnits(),
        toPressureUnits(),
        toPrecipitationUnits(),
        toDistUnits(),
        toTimeUnits()
    )

fun List<Units>.toUnitsDb(): UnitsDb {
    return UnitsDb(
        tempUnits = (this[0] as TempUnits).ordinal,
        windUnits = (this[1] as WindUnits).ordinal,
        pressureUnits = (this[2] as PressureUnits).ordinal,
        precipitationUnits = (this[3] as PrecipitationUnits).ordinal,
        distUnits = (this[4] as DistUnits).ordinal,
        timeUnits = (this[5] as TimeUnits).ordinal
    )
}

fun TempUnits.convertTemp(temp: Double): Int {
    return when(this) {
            C -> temp.roundToInt()
            F -> (temp * 1.8 + 32).roundToInt()
        }
}

fun TimeUnits.convertTime(
    time: Long,
    timeZoneOffset: Long?
): String {
    return when(this) {
        H24 -> getDayTimeAsString(time, timeZoneOffset) { "HH:mm" }
        H12 -> getDayTimeAsString(time, timeZoneOffset) { "hh:mma" }
    }
}

inline fun getDayTimeAsString(
    dayTime: Long,
    timeZoneOffset: Long?,
    pattern: () -> String
) = timeZoneOffset?.let {
        val dt = LocalDateTime.ofEpochSecond(
            dayTime,
            0,
            ZoneOffset.ofTotalSeconds(it.toInt())
        )
        val formatter = DateTimeFormatter.ofPattern(pattern())
        dt.format(formatter)
    } ?: ""

fun PrecipitationUnits.convertPrecipitation(precipitation: Double): Float {
    return when(this) {
        MM -> precipitation.toFloat()
        INCH -> precipitation.toFloat() * 0.04f
    }
}

fun WindUnits.convertWind(windSpeed: Double): Double {
    return when(this) {
        MS -> windSpeed
        KMH -> windSpeed * 3.6
        MIH -> windSpeed * 2.24
    }
}

fun PressureUnits.convertPressure(pressure: Int): Int {
    return when(this) {
        GPA -> pressure
        MMHG -> (pressure * 0.75).roundToInt()
    }
}

fun DistUnits.convertDistance(visibility: Int): Int {
    return when(this) {
        KM -> (visibility * 0.001).roundToInt()
        MI -> (visibility * 0.0006).roundToInt()
    }
}



