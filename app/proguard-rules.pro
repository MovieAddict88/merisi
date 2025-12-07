# Keep application class
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep reflection
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes *Annotation*
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Keep serialization
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep R files
-keep class **.R
-keep class **.R$* {
    <fields>;
}

# Keep BuildConfig
-keep class **.BuildConfig { *; }

# ------------------------------------------------------------------
# Dependencies specific rules
# ------------------------------------------------------------------

# MPAndroidChart
-keep class com.github.mikephil.charting.** { *; }
-keep class com.github.mikephil.charting.data.** { *; }
-keep class com.github.mikephil.charting.utils.** { *; }
-keep class com.github.mikephil.charting.components.** { *; }

# Glide v4
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl

-keep class com.bumptech.glide.load.resource.bitmap.** { *; }
-keep class com.bumptech.glide.load.engine.bitmap_recycle.** { *; }

-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Glide annotations
-keep class com.bumptech.glide.annotation.** { *; }

# zip4j
-keep class net.lingala.zip4j.** { *; }
-keep class net.lingala.zip4j.exception.** { *; }
-keep class net.lingala.zip4j.model.** { *; }
-keep class net.lingala.zip4j.headers.** { *; }

# Play Services Ads
-keep class com.google.android.gms.ads.** { *; }
-keep class com.google.android.gms.common.** { *; }
-keep class com.google.android.gms.internal.ads.** { *; }
-keepattributes *Annotation*

-keepclasseswithmembers class * {
    @com.google.android.gms.common.util.DynamiteApi *;
}

-keep @com.google.android.gms.common.util.DynamiteApi public class * {
  public <fields>;
  public <methods>;
}

-keep public class com.google.android.gms.ads.R$* {
    public static final int *;
}

# AppCompat & Material Design
-keep class androidx.appcompat.** { *; }
-keep class com.google.android.material.** { *; }
-keep public class * extends androidx.appcompat.view.ActionMode

# Keep custom views
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

# Keep onClick methods
-keepclassmembers class * {
    void onClick(android.view.View);
    void onItemClick(android.widget.AdapterView, android.view.View, int, long);
}

# Keep parcelable
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# Keep enum values
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep JSON models (if you have any)
-keepclassmembers class ** {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Keep lambda expressions
-keepclassmembers class ** {
    private static synthetic lambda$*(...);
}

# ------------------------------------------------------------------
# Optional: For VPN/OpenVPN specific classes (adjust based on your actual package)
# ------------------------------------------------------------------
-keep class net.openvpn.openvpn.** { *; }
-keep class * extends android.app.Activity
-keep class * extends android.app.Fragment
-keep class * extends androidx.fragment.app.Fragment

# Keep service and VPN related classes
-keepclassmembers class * extends android.app.Service {
    public void on*(...);
}

# Keep connection/network related classes
-keep class * extends java.lang.Exception {
    <fields>;
    <methods>;
}

# Keep shared preferences
-keepclassmembers class * implements android.content.SharedPreferences {
    public *;
}