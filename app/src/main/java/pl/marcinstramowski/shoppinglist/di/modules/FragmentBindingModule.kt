package pl.marcinstramowski.shoppinglist.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.marcinstramowski.shoppinglist.di.scopes.FragmentScoped
import pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.archivedLists.ArchivedListsFragment
import pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.archivedLists.ArchivedListsModule
import pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.currentLists.CurrentListsFragment
import pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.currentLists.CurrentListsModule

/**
 * Defines fragments with its modules to allow Dagger to inject its dependencies
 */
@Module
abstract class FragmentBindingModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = [CurrentListsModule::class])
    internal abstract fun currentListsFragment(): CurrentListsFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [ArchivedListsModule::class])
    internal abstract fun archivedListsFragment(): ArchivedListsFragment

}