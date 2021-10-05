plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = Android.compileSdk

    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            //            consumerProguardFiles(file('./proguard').listFiles())
        }
    }

    lint {
        isCheckDependencies = true
        isAbortOnError = false
    }
    testOptions {
        unitTests.all {
            it.jvmArgs("-noverify")
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Deps.coroutines)
    implementation(Deps.koinAndroid)
    implementation(Deps.binaryPref)
    implementation(Deps.gson)
    implementation(Deps.room)
    implementation(Deps.roomKtx)
    kapt(Deps.roomCompiler)

    testImplementation(Deps.junit)
    androidTestImplementation(Deps.runner)
    androidTestImplementation(Deps.espresso)
}