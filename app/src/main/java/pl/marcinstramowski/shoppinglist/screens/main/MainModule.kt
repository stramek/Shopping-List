package pl.marcinstramowski.shoppinglist.screens.main

import pl.marcinstramowski.shoppinglist.di.scopes.ActivityScoped
import dagger.Binds
import dagger.Module

/**
 * Specifies classes delivered by dagger when injecting [MainContract] interfaces.
 */
@Module
abstract class MainModule {

    @ActivityScoped
    @Binds
    abstract fun bindLoginPresenter(presenter: MainPresenter): MainContract.Presenter

    @ActivityScoped
    @Binds
    abstract fun bindLoginView(view: MainActivity): MainContract.View
}
