plugins {
    id("com.jraska.module.graph.assertion")
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
}


moduleGraphAssert {
    //https://github.com/jraska/modules-graph-assert
    maxHeight = 4
}

android {
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = "com.ujujzk.easyengmaterial.eeapp"
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
//            proguardFiles(file("./proguard").listFiles())
        }
        debug {
            isMinifyEnabled = false
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

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":domain"))
    implementation(project(":presentation"))
    implementation(project(":flow:main"))
    implementation(project(":flow:dic"))
    implementation(project(":flow:catalog"))
    implementation(project(":flow:translate"))
    implementation(project(":flow:vocabulary"))
    implementation(project(":flow:pack"))
    implementation(project(":flow:store"))
    implementation(project(":flow:learn"))
    implementation(project(":data"))
    implementation(project(":source:local:dictionary"))

    implementation(Deps.material)
    implementation(Deps.koinAndroid)
    implementation(Deps.logger)

    testImplementation(Deps.junit)
    androidTestImplementation(Deps.runner)
    androidTestImplementation(Deps.espresso)
}
