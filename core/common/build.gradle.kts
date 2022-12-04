plugins {
    id("bookstore.android.library")
    id("bookstore.android.library.compose")
    id("bookstore.android.hilt")
}

android {
    namespace = "com.linc.common"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.kotlin.coroutines)
    compileOnly(libs.androidx.compose.ui)
    compileOnly(libs.androidx.compose.ui.text)
}