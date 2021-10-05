plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}


android {
    compileSdk = Deps.androidCompileSdk

    defaultConfig {
        minSdk = Deps.androidMinSdk
        targetSdk = Deps.androidTargetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release") {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        //            consumerProguardFiles(file('./proguard').listFiles())
        }
    }

    lintOptions {
        isAbortOnError = false
    }

    testOptions {
        unitTests.all {
            it.jvmArgs = listOf("-noverify")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Deps.coroutines)
    implementation(Deps.koin)
    implementation(Deps.binaryPref)
    implementation(Deps.gson)
    implementation(Deps.room)
    kapt(Deps.roomCompiler)

    testImplementation(Deps.junit)
    androidTestImplementation(Deps.runner)
    androidTestImplementation(Deps.espresso)
}