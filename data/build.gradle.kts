plugins {
    id("ru.apmgor.forecast.library")
    id("ru.apmgor.forecast.common")
    id("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "ru.apmgor.data"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.room.ktx)
    api(libs.room.runtime)
    kapt(libs.room.compiler)

    api(libs.play.services.location)

    api(libs.bundles.retrofit.all)

    api(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(project(":domain"))

}