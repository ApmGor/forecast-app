package ru.apmgor.domain.model

sealed interface Units {
    val value: Int
}

enum class TempUnits : Units {
    C, F;
    override val value =
        this.ordinal
}

enum class WindUnits : Units {
    MS, KMH, MIH;
    override val value =
        this.ordinal
}

enum class PressureUnits : Units {
    GPA, MMHG;
    override val value =
        this.ordinal
}

enum class PrecipitationUnits : Units {
    MM, INCH;
    override val value =
        this.ordinal
}

enum class DistUnits : Units {
    KM, MI;
    override val value =
        this.ordinal
}

enum class TimeUnits : Units {
    H24, H12;
    override val value =
        this.ordinal
}