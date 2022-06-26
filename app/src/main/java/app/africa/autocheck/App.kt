package app.africa.autocheck

import android.app.Application
import app.africa.autocheck.core.framework.koin.*
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            // use the Android context given there
            androidContext(this@App)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // load modules
            modules(listOf(rootModule, localModule, remoteModule, repositoryModule))
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Picasso.get().isLoggingEnabled = true
        }
    }

}