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

    buildFeatures {
        viewBinding = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    implementation(Deps.appcompat)
    implementation(Deps.ktx)
    implementation(Deps.constraint)
    implementation(Deps.material)
    implementation(Deps.fragment)

    implementation(Deps.coroutines)
    implementation(Deps.corotinesAndoid)

    implementation(Deps.koinAndroid)

    implementation(Deps.cicerone)

    testImplementation(Deps.junit)
    androidTestImplementation(Deps.runner)
    androidTestImplementation(Deps.espresso)
}