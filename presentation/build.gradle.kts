plugins {
    id("ru.apmgor.forecast.library")
    id("ru.apmgor.forecast.common")
}

android {
    namespace = "ru.apmgor.data"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.get()
    }

}

dependencies {

    kapt(libs.hilt.compiler)

    implementation(libs.hilt.navigation.compose)

    implementation(libs.mp.android.chart)

    implementation(libs.lifecycle)

    implementation(project(":domain"))

    api(platform(libs.compose.bom))
    api("androidx.compose.ui:ui")
    api("androidx.compose.material3:material3")
}