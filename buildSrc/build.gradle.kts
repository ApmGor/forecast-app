plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.kotlin.gradle)
    implementation(libs.android.gradle)
    implementation(libs.hilt.gradle)
    implementation(libs.secrets.gradle)
}

gradlePlugin {
    plugins {
        create("commonPlugin") {
            id = "ru.apmgor.forecast.common"
            implementationClass = "ru.apmgor.forecast.plugins.CommonPlugin"
        }
        create("libraryPlugin") {
            id = "ru.apmgor.forecast.library"
            implementationClass = "ru.apmgor.forecast.plugins.LibraryPlugin"
        }
    }
}