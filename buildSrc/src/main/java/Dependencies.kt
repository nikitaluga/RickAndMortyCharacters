object Dependencies {

    object Hilt {
        private const val version = "2.41"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-android-compiler:$version"
    }

    object RecyclerView {
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
        const val adapterDelegates = "com.hannesdorfmann:adapterdelegates4:4.3.0"
        const val animators = "jp.wasabeef:recyclerview-animators:4.0.2"
    }

    object Moshi {
        private const val version = "1.12.0"
        const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$version"
        const val moshiAdapters = "com.squareup.moshi:moshi-adapters:1.11.0"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converterMoshi = "com.squareup.retrofit2:converter-moshi:$version"
        const val interceptor = "com.squareup.okhttp3:logging-interceptor:4.9.0"
    }

    object Coroutines {
        private const val version = "1.6.0"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val services = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$version"
    }

    object Lifecycle {
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0"
    }

    object Navigation {
        private const val version = "2.3.0"
        const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
        const val ui = "androidx.navigation:navigation-ui-ktx:$version"
    }

    object Timber {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
    }

    object ViewBindingHelper {
        const val propertyDelegate = "com.github.kirich1409:viewbindingpropertydelegate:1.5.3"
    }

    object Glide {
        private const val version = "4.12.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object LocalBroadcast {
        const val manager = "androidx.localbroadcastmanager:localbroadcastmanager:1.0.0"
    }
}