plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "crls.finance.coinvalue"
    compileSdk = 33

    defaultConfig {
        applicationId = "crls.finance.coinvalue"
        minSdk = 21
        targetSdk = 33
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //api retrofit permite nos usar get request p exemplo
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //api moshi convert json em kotlin objects
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    //api retrofit with moshi
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    //api coil
    implementation("io.coil-kt:coil:1.3.0")


    //implementation("com.squareup.retrofit2:converter-gson:2.9.0")

}