# Glide specific rules #
# https://github.com/bumptech/glide

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for targeting any API level less than Android API 27
#-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# Glide Transformations
#-dontwarn jp.co.cyberagent.android.gpuimage.**