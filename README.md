## The Movie DB
An Android application which displays a map with current location of user. User can see list of Now Playing Movies and Top Rated Movies by clicking on the marker.

# Supported OS versions:
From Android 5.0 (Lollipop - API level 21) and above

# Supported devices:
Both phones and tablets but the UI is not optimized for tablet yet.
Only support portrait mode

# Supported features:
* Displays map using Google Maps SDK with a marker showing user current location
* Displays list of Now Playing and Top Rated movies
* Displays movies details when clicking on item in movies listing

# Requirements to build the application:
* Android Gradle plugin requires Java 11 to run.
* `secrets.properties` file under project 's root folder for api keys

# Instructions to build:
* Open project with Android Studio
* Make sure project uses Java11
  * Check if Java 11 is installed. If not, please download and install it
  * Open project in Android Studio. Check if project is configured to use Java 11:
    * Right click on root folder > `Open module settings`
    * Click on `SDK location` on the left panel
    * Click on `Gradle Settings` link on the right panel
    * Choose Java 11 's home folder location
* Make sure `secrets.properties` exists in root folder
  * `secrets.properties` is ignored from version control system
  * Please copy `secrets.properties` which is attached to my email to root folder
* Sycn project with Gradle and build

# Project structure
* Contains 3 main layers
  * Presentation: UI related classes (Activities, Fragments, ViewModels, etc...)
  * Domain: business logic (UseCase, Repository interfaces, Data model)
  * Data: Repository implementations and Data Transfer Objects
* Implemented using **MVVM** architecture.
* Single activity hosts all fragments. Each fragment represents a different screen of application
* Use Jetpack Navigation Component to manage and navigate between fragments

# Libraries
* [Foundation][jetpack] - Components for core system capabilities, Kotlin extensions and support for
  multidex and automated testing.
  * [AppCompat][appcompat] - Degrade gracefully on older versions of Android.
  * [Android KTX][ktx] - Write more concise, idiomatic Kotlin code.
* [Architecture][arch] - A collection of libraries that help you design robust, testable, and
  maintainable apps. Start with classes for managing your UI component lifecycle and handling data
  persistence.
  * [Lifecycles][lifecycle] - Create a UI that automatically responds to lifecycle events.
  * [LiveData][livedata] - Build data objects that notify views when the underlying database changes.
  * [Navigation][navigation] - Handle everything needed for in-app navigation.
  * [ViewModel][viewmodel] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
  * [DataStore][datastore] - store data asynchronously, consistently, and transactionally using Kotlin coroutines and Flow.
* Third party and miscellaneous libraries
  * [Hilt][hilt] for dependency injection
  * [Kotlin Coroutines][coroutines] for managing background threads with simplified code and reducing needs for callbacks
  * [Retrofit][retrofit] for REST api communication
  * [Glide][glide] for image loading
  * [Steho][stetho] debug bridge for Android Application
  * [Timber][timber] a logger with a small, extensible API which provides utility on top of Android's normal `Log` class.
  * [Mockk][mockk] for mocking in tests
  * [MockWebServer][mockwebserver] A scriptable web server for testing HTTP clients

# What has been done
* Location Feature
  * Can display map using Google Maps SDK
  * Can display current location with marker
* Movie Listing Feature
  * Can display list of `Now Playing` movies
  * Can display list of `Top Rated` movies
  * Can display error dialog in failure cases. Current code can cover network error, api error code. Other error will display a general error message
  * Can pull to refresh
  * Can fetch list of movies page by page
  * API token is added to request automatically using Interceptor
  * Fake advertisement UI
  * Unit Test
  * Mocking API response by using MockWebServer
* Movie Details Feature
  * Can display movie details: image, title, release year, duration, generes, description, rating
  * Can display `Cast and Crews` section:
    * List of casts and crews which allow to see all/see less
    * Display single or multiple director 's names with specific rule
    * Unit test
  * Can pull to refresh
* Additional: fetch and save configuration for generating full image url
  * Call api to get configuration and save to local storage using [DataStore][datastore]
  * API call is executed during Splash waiting time

# What has been missed
* Location Feature:
  * Enter a location to display on map
  * Draw direction between current location and entered location
  * More cases to check when handle requesting permission, GPS on/off, etc...
* Settings feature

# What can be improved
* Caching mechanism (Retrofit cache) to reduce number of API calls
* Single source of truth using database to cache and store data in local storage for offline mode
* UI Test is missing

[jetpack]: https://developer.android.com/jetpack/components
[appcompat]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[ktx]: https://developer.android.com/kotlin/ktx
[arch]: https://developer.android.com/jetpack/arch/
[lifecycle]: https://developer.android.com/topic/libraries/architecture/lifecycle
[livedata]: https://developer.android.com/topic/libraries/architecture/livedata
[navigation]: https://developer.android.com/topic/libraries/architecture/navigation/
[room]: https://developer.android.com/topic/libraries/architecture/room
[viewmodel]: https://developer.android.com/topic/libraries/architecture/viewmodel
[coroutines]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[datastore]: https://developer.android.com/topic/libraries/architecture/datastore?gclid=Cj0KCQjwwY-LBhD6ARIsACvT72N1ymnF-p0069IiGTsjarzspbtREuNV9YBGqrBdmVT2qoNvUtw90UsaAmO_EALw_wcB&gclsrc=aw.ds

[support-lib]: https://developer.android.com/topic/libraries/support-library/index.html
[arch]: https://developer.android.com/arch
[hilt]: https://dagger.dev/hilt/
[mockk]: https://github.com/mockk/mockk
[coroutines]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[glide]: https://bumptech.github.io/glide/
[retrofit]: http://square.github.io/retrofit
[stetho]: https://github.com/facebook/stetho
[timber]: https://github.com/JakeWharton/timber
[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[robolectric]: https://github.com/robolectric/robolectric
[defaultlifecycleobserver]: https://developer.android.com/reference/android/arch/lifecycle/DefaultLifecycleObserver