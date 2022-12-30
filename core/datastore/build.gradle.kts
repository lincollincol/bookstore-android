plugins {
    id("bookstore.android.library")
    id("bookstore.android.hilt")
}

android {
    namespace = "com.linc.datastore"
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.kotlin.coroutines)
    implementation(libs.datastore.preferences)
}