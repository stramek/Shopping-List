package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.archivedLists

import dagger.Binds
import dagger.Module
import pl.marcinstramowski.shoppinglist.di.scopes.FragmentScoped

/**
 * Specifies classes delivered by dagger when injecting [ArchivedListsContract] interfaces.
 */
@Module
abstract class ArchivedListsModule {

    @FragmentScoped
    @Binds
    abstract fun bindPresenter(presenter: ArchivedListsPresenter): ArchivedListsContract.Presenter

    @FragmentScoped
    @Binds
    abstract fun bindView(view: ArchivedListsFragment): ArchivedListsContract.View

}