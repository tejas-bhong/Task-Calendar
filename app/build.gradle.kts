import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.navigation.safe.args)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.tejasbhong.calendar"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tejasbhong.calendar"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val apiPropertiesFile = project.rootProject.file("api.properties")
        val properties = Properties()
        properties.load(apiPropertiesFile.inputStream())
        val apiBaseUrl = properties.getProperty("API_BASE_URL") ?: ""
        buildConfigField(
            type = "String",
            name = "API_BASE_URL",
            value = apiBaseUrl
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

//    sourceSets {
//        getByName("main") {
//            java.srcDirs("build/generated/source/navigation-args")
//        }
//    }

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
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    // Ensure kapt works correctly with Hilt
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
//    implementation(libs.hilt.lifecycle.viewmodel)
    implementation(libs.hilt.work)
    implementation(libs.androidx.work.runtime)
    kapt(libs.hilt.compiler.androidx)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.core)
    testImplementation(libs.androidx.core.testing)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

kapt {
    correctErrorTypes = true
}
