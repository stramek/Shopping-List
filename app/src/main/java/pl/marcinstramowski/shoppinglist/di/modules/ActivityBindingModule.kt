package pl.marcinstramowski.shoppinglist.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.marcinstramowski.shoppinglist.di.scopes.ActivityScoped
import pl.marcinstramowski.shoppinglist.screens.listDetails.ListDetailsActivity
import pl.marcinstramowski.shoppinglist.screens.listDetails.ListDetailsModule
import pl.marcinstramowski.shoppinglist.screens.main.MainActivity
import pl.marcinstramowski.shoppinglist.screens.main.MainModule

/**
 * Defines Activities with its modules to allow Dagger to inject its dependencies
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(MainModule::class)])
    internal abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(ListDetailsModule::class)])
    internal abstract fun listDetailsActivity(): ListDetailsActivity
}
