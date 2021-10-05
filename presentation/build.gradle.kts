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
            //consumerProguardFiles(file('./proguard').listFiles())
        }
    }

    lintOptions {
        isAbortOnError = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
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
    implementation (project(":domain"))
    
    implementation(Deps.appcompat)
    implementation(Deps.ktx)
    implementation(Deps.constraint)
    implementation(Deps.recyclerview)
    implementation(Deps.material)
    implementation(Deps.fragment)

    implementation(Deps.lcCommon)

    implementation(Deps.koinAndroid)

    implementation(Deps.glide)
    kapt(Deps.glideCompiler)
//    implementation(Deps.glideOkhttp3

    implementation(Deps.cicerone)

    testImplementation(Deps.junit)
    androidTestImplementation(Deps.runner)
    androidTestImplementation(Deps.espresso)
}
