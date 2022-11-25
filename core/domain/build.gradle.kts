plugins {
    id("bookstore.android.library")
    id("bookstore.android.hilt")
}

android {
    namespace = "com.linc.domain"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.kotlin.coroutines)

}