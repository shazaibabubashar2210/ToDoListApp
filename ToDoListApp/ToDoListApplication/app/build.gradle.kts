plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services) // Firebase plugin
}

android {
    namespace = "com.example.todolistapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todolistapplication"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    // Enable View Binding (Kotlin DSL)
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Firebase dependencies
    implementation(platform("com.google.firebase:firebase-bom:32.2.0")) // BOM
    implementation("com.google.firebase:firebase-database-ktx")         // Firebase Realtime Database
    implementation("com.google.firebase:firebase-auth-ktx")            // Firebase Authentication
    implementation("com.google.firebase:firebase-analytics-ktx")       // Firebase Analytics (optional)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Ensure the google-services plugin is applied
apply(plugin = "com.google.gms.google-services")
