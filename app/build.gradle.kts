plugins {
    id("bookstore.android.application")
    id("bookstore.android.application.compose")
    id("bookstore.android.hilt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.linc.bookstore"
    defaultConfig {
        applicationId = "com.linc.bookstore"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        create("release") {
            storeFile = rootProject.file("keystore/bookstore.jks")
            keyAlias = "-"
            keyPassword = "-"
            storePassword = "-"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = true
            applicationIdSuffix = ".prod"
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))

    implementation(project(":feature:auth"))
    implementation(project(":feature:books"))
    implementation(project(":feature:bookdetails"))
    implementation(project(":feature:cart"))
    implementation(project(":feature:preferences"))
    implementation(project(":feature:interests"))
    implementation(project(":feature:subjectbooks"))
    implementation(project(":feature:bookmarks"))
    implementation(project(":feature:languagepicker"))
    implementation(project(":feature:payments"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.motion.compose.core)
    implementation(libs.stripe)


}