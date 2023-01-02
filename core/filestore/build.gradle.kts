plugins {
    id("bookstore.android.library")
    id("bookstore.android.hilt")
}

android {
    namespace = "com.linc.filestore"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.kotlin.coroutines)
    implementation(libs.gson)

}