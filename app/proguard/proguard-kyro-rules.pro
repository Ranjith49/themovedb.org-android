# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Android_SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# KRYO + SNAPY DB
# http://stackoverflow.com/questions/31676286/exception-on-array-serialization-using-proguard-android

	-dontshrink
    -verbose
    -dontwarn sun.reflect.**
    -dontwarn java.beans.**
    -keep,allowshrinking class com.esotericsoftware.** {
       <fields>;
       <methods>;
    }
    -keep,allowshrinking class java.beans.** { *; }
    -keep,allowshrinking class sun.reflect.** { *; }
    -keep,allowshrinking class com.esotericsoftware.kryo.** { *; }
    -keep,allowshrinking class com.esotericsoftware.kryo.io.** { *; }
    -keep,allowshrinking class sun.nio.ch.** { *; }
    -dontwarn sun.nio.ch.**
    -dontwarn sun.misc.**

    -keep,allowshrinking class com.snappydb.** { *; }
    -dontwarn com.snappydb.**
