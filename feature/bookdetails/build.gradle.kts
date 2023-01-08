plugins {
    id("bookstore.android.feature")
    id("bookstore.android.library.compose")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.linc.bookdetails"
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
    implementation(project(":core:payments"))
    implementation(libs.palette)
}