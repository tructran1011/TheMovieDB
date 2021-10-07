##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
-keep,allowobfuscation @interface com.google.gson.annotations.SerializedName
##---------------End: proguard configuration for Gson  ----------


##---------------Begin: proguard configuration for Navigation component  ----------
# need to keep NavHostFragment class since we could use it as name="androidx.navigation.fragment.NavHostFragment" in the xml
-keep class androidx.navigation.fragment.NavHostFragment

# For safe agrument
-keepnames class * extends android.os.Parcelable
-keepnames class * extends java.io.Serializable
##---------------End: proguard configuration for Navigation component  ----------

##---------------Begin: proguard configuration for DataStore  ----------
-keepclassmembers class * extends androidx.datastore.preferences.protobuf.GeneratedMessageLite {
    <fields>;
}
##---------------End: proguard configuration for DataStore  ----------