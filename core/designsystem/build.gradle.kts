plugins {
    id("bookstore.android.library")
    id("bookstore.android.library.compose")
}

android {
    namespace = "com.linc.designsystem"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.toolingPreview)
    debugApi(libs.androidx.compose.ui.tooling)
}