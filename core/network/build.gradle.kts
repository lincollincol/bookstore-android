plugins {
    id("bookstore.android.library")
    id("bookstore.android.hilt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.linc.network"
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        consumerProguardFile("network-proguard-rules.pro")
    }
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {

    implementation(project(":core:common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.kotlin.coroutines)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)

}