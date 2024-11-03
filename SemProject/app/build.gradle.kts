plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services) // Ensure correct Firebase setup
}

android {
    namespace = "com.example.todolistapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todolistapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true // Enable multidex here

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Core AndroidX libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material) // Ensure material dependency is correct

    // AndroidX activity and constraint layout
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Navigation components
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Firebase database support
    implementation(libs.firebase.database)

    // Multidex support
    implementation("androidx.multidex:multidex:2.0.1")
    implementation(libs.firebase.auth)

    // Core AndroidX libraries for testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Main dependencies
    implementation("androidx.recyclerview:recyclerview:1.3.0") // Ensure this is included for the main app
    implementation("com.google.android.material:material:1.9.0") // Ensure this points to the correct version
}

// Force dependencies to use a specific version to avoid conflicts
configurations.all {
    resolutionStrategy {
        force("androidx.recyclerview:recyclerview:1.3.0")
    }
}
