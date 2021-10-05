plugins {
    id("kotlin")
}

dependencies {

    implementation(project(":domain"))
//    implementation project(':data')

    implementation(Deps.coroutines)
    implementation(Deps.koin)

    implementation(Deps.gson)
    implementation(Deps.json)

    implementation(Deps.okhttp)
    implementation(Deps.retrofit)
    implementation(Deps.retrofitGson)

    testImplementation(Deps.junit)
}