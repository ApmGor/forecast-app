plugins {
    id("ru.apmgor.forecast.library")
    id("ru.apmgor.forecast.common")
}

android {
    namespace = "ru.apmgor.data"
}

dependencies {
    kapt(libs.hilt.compiler)

    implementation(project(":data"))
    implementation(project(":domain"))
}