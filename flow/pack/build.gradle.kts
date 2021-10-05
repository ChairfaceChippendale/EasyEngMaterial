plugins {
    id("com.android.library")
    kotlin("android")
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
            )//            consumerProguardFiles(file('./proguard').listFiles())
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
    buildFeatures.viewBinding = true
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