plugins {
    id("bookstore.android.feature")
    id("bookstore.android.library.compose")
}

android {
    namespace = "com.linc.subjectbooks"
}

dependencies {
    implementation(libs.bundles.paging)
}