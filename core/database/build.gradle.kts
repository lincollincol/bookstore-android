plugins {
    id("bookstore.android.library")
    id("bookstore.android.hilt")
}

android {
    namespace = "com.linc.database"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.kotlin.coroutines)

    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

}