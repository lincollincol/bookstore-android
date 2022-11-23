plugins {
    id("bookstore.android.library")
    id("bookstore.android.hilt")
}

android {
    namespace = "com.linc.network"
}

dependencies {

    implementation(libs.androidx.core)
    implementation(libs.bundles.kotlin.coroutines)

}