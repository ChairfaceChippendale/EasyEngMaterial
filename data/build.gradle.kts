plugins {
    id("kotlin")
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":domain"))

    implementation(Deps.coroutines)
    implementation(Deps.koin)

    implementation(Deps.gson)
    implementation(Deps.json)
    implementation(Deps.okhttp)

    testImplementation(Deps.junit)
}