plugins{
   id("kotlin")
}

dependencies {

    implementation (project(":domain"))
//    implementation project(':data')

    implementation(Deps.coroutines)
    implementation(Deps.koin)

    implementation(Deps.gson)
    implementation(Deps.json)

    implementation(Deps.okhttp)
    implementation(Deps.retrofit)
    implementation(Deps.retrofitGson)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {

        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi"
        )
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}