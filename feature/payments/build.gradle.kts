plugins {
    id("bookstore.android.feature")
    id("bookstore.android.library.compose")
}

android {
    namespace = "com.linc.payments"
}

dependencies {
    implementation(libs.stripe)
}