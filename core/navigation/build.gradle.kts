plugins {
    id("bookstore.android.library")
    id("bookstore.android.library.compose")
    id("bookstore.android.hilt")
}

android {
    namespace = "com.linc.navigation"
}

dependencies {
    implementation(project(":core:common"))
    compileOnly(libs.androidx.lifecycle.runtime.compose)
    compileOnly(libs.androidx.navigation.compose)
    compileOnly(libs.motion.compose.navigation)
}