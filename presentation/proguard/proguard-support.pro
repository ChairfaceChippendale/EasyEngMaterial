### Support v7, Design
# http://stackoverflow.com/questions/29679177/cardview-shadow-not-appearing-in-lollipop-after-obfuscate-with-proguard/29698051
-keep class android.support.v7.widget.RoundRectDrawable { *; }

-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

-dontwarn android.support.**
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

# https://github.com/Gericop/Android-Support-Preference-V7-Fix/blob/master/preference-v7/proguard-rules.pro
-keepclassmembers class android.support.v7.preference.PreferenceGroupAdapter {
    private ** mPreferenceLayouts;
}
-keepclassmembers class android.support.v7.preference.PreferenceGroupAdapter$PreferenceLayout {
    private int resId;
    private int widgetResId;
}

# https://github.com/dandar3/android-support-animated-vector-drawable/blob/master/proguard-project.txt
#-keepclassmembers class android.support.graphics.drawable.VectorDrawableCompat$* {
#   void set*(***);
#   *** get*();
#}