package pl.marcinstramowski.shoppinglist.di

import pl.marcinstramowski.shoppinglist.screens.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.marcinstramowski.shoppinglist.screens.main.MainActivity

/**
 * Defines Activities with its modules to allow Dagger to inject its dependencies
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(MainModule::class)])
    internal abstract fun mainActivity(): MainActivity
}
