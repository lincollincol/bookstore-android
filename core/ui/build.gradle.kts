plugins {
    id("bookstore.android.library")
    id("bookstore.android.library.compose")
}

android {
    namespace = "com.linc.ui"
}

dependencies {

    implementation(project(":core:designsystem"))
//    compileOnly(libs.androidx.compose.ui)
//    compileOnly(libs.androidx.compose.ui.text)

}