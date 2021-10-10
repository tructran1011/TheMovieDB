package com.me.themoviedb.buildsrc

object Deps {
    const val GRADLE = "com.android.tools.build:gradle:7.0.2"
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31"

    const val MATERIAL_DESIGN = "com.google.android.material:material:1.4.0"
    const val TIMBER = "com.jakewharton.timber:timber:5.0.1"
    const val DATA_STORE = "androidx.datastore:datastore-preferences:1.0.0"

    // For Test
    const val JUNIT = "junit:junit:4.13.2"
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1"
    const val HAMCREST = "org.hamcrest:hamcrest-all:1.3"
    const val MOCKK = "io.mockk:mockk:1.12.0"
    const val ROBOLECTRIC = "org.robolectric:robolectric:4.6.1"
    const val MOCK_WEB_SERVER = "com.squareup.okhttp3:mockwebserver:4.9.2"

    object AndroidX {
        const val CORE_KTX = "androidx.core:core-ktx:1.6.0"
        const val APP_COMPAT = "androidx.appcompat:appcompat:1.3.1"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.0"
        const val TEST_EXT_JUNIT = "androidx.test.ext:junit:1.1.3"
        const val TEST_ESPRESSO = "androidx.test.espresso:espresso-core:3.4.0"
        const val CORE_TESTING = "androidx.arch.core:core-testing:2.1.0"
        const val SWIPE_REFRESH_LAYOUT = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
    }

    object Lifecycle {
        const val RUN_TIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
        const val VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0-alpha03"
        const val LIVE_DATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
        const val LIFECYCLE_COMMON_JAVA8 = "androidx.lifecycle:lifecycle-common-java8:2.3.1"
    }

    object Retrofit {
        private const val VERSION = "2.9.0"
        const val RETROFIT = "com.squareup.retrofit2:retrofit:$VERSION"
        const val GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:$VERSION"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:$VERSION"
    }

    object Stetho {
        private const val VERSION = "1.5.1"
        const val STETHO = "com.facebook.stetho:stetho:$VERSION"
        const val STETHO_OKHTTP = "com.facebook.stetho:stetho-okhttp3:$VERSION"
    }

    object Navigation {
        const val VERSION = "2.3.5"
        const val FRAGMENT_KTX =  "androidx.navigation:navigation-fragment-ktx:$VERSION"
        const val UI_KTX = "androidx.navigation:navigation-ui-ktx:$VERSION"
        const val SAFE_ARGS_PLUGIN = "androidx.navigation:navigation-safe-args-gradle-plugin:$VERSION"
    }

    object Hilt {
        private const val VERSION = "2.38.1"
        const val PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:$VERSION"
        const val HILT_ANDROID = "com.google.dagger:hilt-android:$VERSION"
        const val HILT_COMPILER = "com.google.dagger:hilt-compiler:$VERSION"
    }

    object Room {
        private const val VERSION = "2.3.0"

        const val RUNTIME = "androidx.room:room-runtime:$VERSION"
        const val KTX = "androidx.room:room-ktx:$VERSION"
        const val COMPILER =  "androidx.room:room-compiler:$VERSION"
    }

    object Glide {
        private const val VERSION = "4.12.0"
        const val GLIDE = "com.github.bumptech.glide:glide:$VERSION"
        const val COMPILER = "com.github.bumptech.glide:compiler:$VERSION"
    }
}