plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.20-1.0.25"
}

android {
    namespace = "com.st11.expensetracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.st11.expensetracker"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.work.runtime.ktx) //for notification
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //lottie file animations
    implementation("com.github.LottieFiles:dotlottie-android:0.5.0")

    implementation("com.airbnb.android:lottie-compose:6.1.0")


    //  this is for Material2 design
    implementation("com.google.android.material:material:1.12.0")

    // Koin dependencies for dependency injection
    // Koin Core (required)
    implementation("io.insert-koin:koin-android:3.5.0")

    // Koin for Jetpack Compose
    implementation("io.insert-koin:koin-androidx-compose:3.5.0")

    // Koin for Navigation in Jetpack Compose
    implementation("io.insert-koin:koin-androidx-navigation:3.5.0")

    // Lifecycle (to work with ViewModel)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Room dependencies
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // For coroutine support (if you're using suspend functions in DAO)
    implementation("androidx.room:room-ktx:2.6.1")

    //animation
    implementation("com.google.accompanist:accompanist-navigation-animation:0.31.2-alpha")

    //pager APi
    implementation("androidx.compose.foundation:foundation:1.7.8") // Use latest version

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.2-alpha")

    //animation
    implementation("com.google.accompanist:accompanist-navigation-animation:0.31.2-alpha")

//for Dynamic Status Bar colors
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.2-alpha")

    //font Awesome Icons
    implementation("br.com.devsrsouza.compose.icons:font-awesome:1.1.1")

    // DataStore for storing preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Accompanist Pager (for horizontal paging)
    implementation("com.google.accompanist:accompanist-pager:0.25.0")

    // Accompanist Pager Indicators (for page indicators like dots)
    implementation("com.google.accompanist:accompanist-pager-indicators:0.25.0")

//    //for gif display
//    implementation("io.coil-kt:coil-compose:2.4.0")
//    implementation("io.coil-kt:coil-gif:2.4.0") // Important: For GIF support
//
//
////    //country code phone no
////    implementation ("com.github.joielechong:countrycodepicker:2.4.2")
////
//////    libphonenumber-android is an Android port of Google’s libphonenumber library, which provides phone number parsing, formatting, and validation
////    implementation("io.michaelrocks:libphonenumber-android:8.13.27")
//    implementation("io.michaelrocks:libphonenumber-android:8.13.27")
//
//
//    implementation("com.patrykandpatrick.vico:compose:2.1.2") // Use latest version
//    implementation("com.patrykandpatrick.vico:core:2.1.2")
//    implementation("com.patrykandpatrick.vico:views:2.1.2") // Only if you use View system
//
}