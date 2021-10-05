plugins {
    id("kotlin")
}

dependencies {

    implementation(Deps.coroutines)
    implementation(Deps.koin)

    testImplementation(Deps.junit)
}
