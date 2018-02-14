package pl.marcinstramowski.shoppinglist

import com.squareup.leakcanary.LeakCanary
import dagger.android.DaggerApplication
import pl.marcinstramowski.shoppinglist.di.components.DaggerAppComponent
import timber.log.Timber

/**
 * Class for maintaining global Shopping List application state
 */
class ShoppingListApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) return
        LeakCanary.install(this)
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector() = DaggerAppComponent.builder().application(this).build()
}