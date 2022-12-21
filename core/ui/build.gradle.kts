plugins {
    id("bookstore.android.library")
    id("bookstore.android.library.compose")
}

android {
    namespace = "com.linc.ui"
}

dependencies {

    implementation(project(":core:designsystem"))
    api(libs.cloudy)
    api(libs.motion.compose.core)
    api(libs.motion.compose.navigation)
    api(libs.coil)
    api(libs.coil.compose)
    api(libs.accompanist.flowlayout)
    api(libs.androidx.constraintlayout.compose)
    compileOnly(libs.palette)

}