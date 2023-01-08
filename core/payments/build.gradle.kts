plugins {
    id("bookstore.android.library")
    id("bookstore.android.library.compose")
    id("bookstore.android.hilt")
}

android {
    namespace = "com.linc.core.payments"
}

dependencies {
    compileOnly(libs.androidx.compose.runtime)
    compileOnly(libs.androidx.compose.ui)
    compileOnly(libs.androidx.activity.compose)
    api(libs.stripe)
}