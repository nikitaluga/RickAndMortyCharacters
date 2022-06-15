plugins {
    id ("com.android.application")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("androidx.navigation.safeargs.kotlin")
    id ("kotlinx-serialization")
    id ("kotlin-parcelize")
    id ("dagger.hilt.android.plugin")
}

repositories {
    google()
    mavenCentral()
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId  = "com.rickandmortycharacters"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures.viewBinding = true
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    testImplementation("junit:junit:4+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Hilt
    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.compiler)

    //RecyclerView
    implementation(Dependencies.RecyclerView.recyclerView)
    implementation(Dependencies.RecyclerView.adapterDelegates)
    implementation(Dependencies.RecyclerView.animators)

    // Moshi
    kapt(Dependencies.Moshi.codegen)
    implementation(Dependencies.Moshi.moshiKotlin)
    implementation(Dependencies.Moshi.moshiAdapters)

    // Retrofit
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.converterMoshi)
    implementation(Dependencies.Retrofit.interceptor)

    // Coroutines
    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Coroutines.android)
    implementation(Dependencies.Coroutines.services)

    // Lifecycles only (without ViewModel or LiveData)
    implementation(Dependencies.Lifecycle.lifecycle)

    //Navigation
    implementation(Dependencies.Navigation.fragment)
    implementation(Dependencies.Navigation.ui)

    //Timber
    implementation(Dependencies.Timber.timber)

    // ViewBinding helper
    implementation(Dependencies.ViewBindingHelper.propertyDelegate)

    //Glide
    implementation(Dependencies.Glide.glide)
    kapt(Dependencies.Glide.compiler)

    // Check Internet connection
    implementation(Dependencies.LocalBroadcast.manager)
}