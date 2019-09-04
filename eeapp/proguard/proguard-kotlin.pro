### Kotlin
#https://gist.github.com/jemshit/767ab25a9670eb0083bafa65f8d786bb
#https://stackoverflow.com/questions/33547643/how-to-use-kotlin-with-proguard
#https://medium.com/@AthorNZ/kotlin-metadata-jackson-and-proguard-f64f51e5ed32
#https://medium.com/@maheshwar.ligade/progurad-for-android-kotlin-104a1169fdcd

#keep annotation classes and not warn for reflection classes.
-keep class kotlin.** { *; }
-keep class kotlin.reflect.jvm.internal.** { *; }
-dontwarn kotlin.**
-dontwarn kotlin.reflect.jvm.internal.**

#issue with kotlin MetaData. Especially in case of Jackson kotlin module.
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keep class kotlin.Metadata { *; }
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

#enum
-keepclassmembers class **$WhenMappings {
    <fields>;
}

-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}