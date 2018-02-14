package pl.marcinstramowski.shoppinglist.screens.listDetails

import dagger.Binds
import dagger.Module
import pl.marcinstramowski.shoppinglist.di.scopes.ActivityScoped

/**
 * Specifies classes delivered by dagger when injecting [ListDetailsContract] interfaces.
 */
@Module
abstract class ListDetailsModule {

    @ActivityScoped
    @Binds
    abstract fun bindListDetailsPresenter(presenter: ListDetailsPresenter): ListDetailsContract.Presenter

    @ActivityScoped
    @Binds
    abstract fun bindListDetailsView(view: ListDetailsActivity): ListDetailsContract.View

}