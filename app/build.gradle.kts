plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias (libs.plugins.kotlin.spotless)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.dutch.volumancer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dutch.volumancer"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    tasks.named("detekt").configure { enabled = false }

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
    }
    spotless {
        kotlin {
            target("**/*.kt")
//            ktlint("0.50.0")
            trimTrailingWhitespace()
            endWithNewline()
            leadingSpacesToTabs()
        }
    }

    detekt {
        toolVersion = libs.versions.detektVersion.get()
        config = files("$rootDir/detekt.yml")
        buildUponDefaultConfig = true
        allRules = false

        reports {
            html.required.set(true)
            xml.required.set(true)
            txt.required.set(true)
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.mockito)
    testImplementation(libs.mockito.kotlin)
}