plugins {
    id("ru.apmgor.forecast.library")
    id("com.google.dagger.hilt.android")
    id("ru.apmgor.forecast.common")
}

android {
    namespace = "ru.apmgor.data"
}

dependencies {
    api(libs.hilt.android)
    kapt(libs.hilt.compiler)
}