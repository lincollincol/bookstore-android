plugins {
    id("bookstore.android.feature")
    id("bookstore.android.library.compose")
}

android {
    namespace = "com.linc.preferences"

}

dependencies {
    implementation(libs.androidx.appcompat)

}