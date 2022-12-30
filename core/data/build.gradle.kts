plugins {
    id("bookstore.android.library")
    id("bookstore.android.hilt")
}

android {
    namespace = "com.linc.data"
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.kotlin.coroutines)
    implementation(libs.bundles.paging)

}