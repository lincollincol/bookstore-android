plugins {
    id("bookstore.android.feature")
    id("bookstore.android.library.compose")
}

android {
    namespace = "com.linc.cart"
}

dependencies {
    implementation(project(":core:payments"))
}