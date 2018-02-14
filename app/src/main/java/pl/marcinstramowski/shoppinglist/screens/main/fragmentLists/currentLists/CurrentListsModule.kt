package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.currentLists

import dagger.Binds
import dagger.Module
import pl.marcinstramowski.shoppinglist.di.scopes.FragmentScoped

/**
 * Specifies classes delivered by dagger when injecting [CurrentListsContract] interfaces.
 */
@Module
abstract class CurrentListsModule {

    @FragmentScoped
    @Binds
    abstract fun bindPresenter(presenter: CurrentListsPresenter): CurrentListsContract.Presenter

    @FragmentScoped
    @Binds
    abstract fun bindView(view: CurrentListsFragment): CurrentListsContract.View

}