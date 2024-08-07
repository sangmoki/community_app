plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.sangmoki.community_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sangmoki.community_app"
        minSdk = 23
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

    dataBinding {
        enable = true
    }
}

dependencies {

    // firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    // firebase analytics
    implementation("com.google.firebase:firebase-analytics")
    // firebase auth
    implementation("com.google.firebase:firebase-auth-ktx")
    // firebase realtime database
    implementation("com.google.firebase:firebase-database-ktx")
    // firebase storage
    implementation("com.google.firebase:firebase-storage")
    // firebase storage ui
    implementation ("com.firebaseui:firebase-ui-storage:7.2.0")
    // glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}