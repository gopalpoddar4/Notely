plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.gopalpoddar4.notely"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.gopalpoddar4.notely"
        minSdk = 26
        targetSdk = 34
        versionCode = 17
        versionName = "17.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

        // ViewModel
        implementation ("androidx.lifecycle:lifecycle-viewmodel:2.8.7")
        // LiveData
        implementation("androidx.lifecycle:lifecycle-livedata:2.8.7")
        // Room components
        implementation ("androidx.room:room-runtime:2.2.5")
    implementation(libs.firebase.messaging)
    annotationProcessor ("androidx.room:room-compiler:2.2.5")
        implementation("com.airbnb.android:lottie:6.6.2")



    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}