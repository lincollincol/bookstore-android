plugins {
    id("bookstore.android.feature")
    id("bookstore.android.library.compose")
}

android {
    namespace = "com.linc.books"
}

dependencies {
    implementation(libs.bundles.paging)
}