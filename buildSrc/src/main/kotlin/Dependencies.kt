object Android {
    const val minSdk = 23
    const val compileSdk = 31
    const val targetSdk = 31
}

object Deps {

    const val coroutinesVer = "1.5.2-native-mt"

    //jetpack
    private const val materialVer = "1.4.0"
    private const val fragmentVer = "1.3.6"
    private const val activityVer = "1.3.1"
    private const val lifecycleVer = "2.3.1"

    //network
    private const val gsonVer = "2.8.8"
    private const val jsonVer = "20190722"
    private const val okhttpVer = "4.2.2"
    private const val retrofitVer = "2.9.0"
    private const val glideVer = "4.12.0"
    private const val chuckVer = "1.1.0"

    //utils
    private const val koinVer = "3.1.2"

    //storage
    private const val roomVer = "2.3.0"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVer"
    const val corotinesAndoid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVer"

    const val material = "com.google.android.material:material:$materialVer"
    const val appcompat = "androidx.appcompat:appcompat:1.3.1"
    const val ktx = "androidx.core:core-ktx:1.7.0-beta02"
    const val constraint = "androidx.constraintlayout:constraintlayout:2.1.1"
    const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"

    const val activity = "androidx.activity:activity:$activityVer"
    const val activityKtx = "androidx.activity:activity-ktx:$activityVer"
    const val fragment = "androidx.fragment:fragment-ktx:$fragmentVer"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:$fragmentVer"
    const val fragmentTest = "androidx.fragment:fragment-testing:$fragmentVer"

    const val lcViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVer"
    const val lcLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVer"
    const val lcRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVer"
    const val lcCommon = "androidx.lifecycle:lifecycle-common-java8:$lifecycleVer"

    const val inset = "dev.chrisbanes:insetter-ktx:0.3.1"

    const val koin = "io.insert-koin:koin-core:$koinVer"
    const val koinAndroid = "io.insert-koin:koin-android:$koinVer"

    const val glide = "com.github.bumptech.glide:glide:$glideVer"
    const val glideCompiler = "com.github.bumptech.glide:compiler:$glideVer"
//    const val glideOkhttp3 = "com.github.bumptech.glide:okhttp3-integration:${glideVer}"

    const val cicerone = "com.github.terrakok:cicerone:6.5"

    const val gson = "com.google.code.gson:gson:$gsonVer"
    const val json = "org.json:json:$jsonVer"
    const val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVer"
//    const val okhttpInterceptor: "com.squareup.okhttp3:logging-interceptor:$okhttpVer"
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVer"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:$retrofitVer"
//    const val chuckDebug = "com.readystatesoftware.chuck:library:$chuckVer"
//    const val chuckRelease = "com.readystatesoftware.chuck:library-no-op:$chuckVer"

    const val binaryPref = "com.github.iamironz:binaryprefs:1.0.1"

    const val room = "androidx.room:room-runtime:$roomVer"
    const val roomKtx = "androidx.room:room-ktx:$roomVer"
    const val roomCompiler = "androidx.room:room-compiler:$roomVer"

    const val logger = "com.orhanobut:logger:2.2.0"

    const val junit = "junit:junit:4.13.2"
    const val runner = "androidx.test:runner:1.4.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
}

