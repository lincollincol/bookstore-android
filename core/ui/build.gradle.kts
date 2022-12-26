plugins {
    id("bookstore.android.library")
    id("bookstore.android.library.compose")
    id("bookstore.android.hilt")
}

android {
    namespace = "com.linc.ui"
}

dependencies {

    implementation(libs.androidx.core.ktx)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.toolingPreview)
    debugApi(libs.androidx.compose.ui.tooling)

    api(libs.cloudy)
    api(libs.motion.compose.core)
    api(libs.motion.compose.navigation)
    api(libs.coil)
    api(libs.coil.compose)
    api(libs.accompanist.flowlayout)
    api(libs.androidx.constraintlayout.compose)

    compileOnly(libs.palette)

}