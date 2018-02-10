package pl.marcinstramowski.shoppinglist.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.marcinstramowski.shoppinglist.screens.main.currentLists.CurrentListsFragment
import pl.marcinstramowski.shoppinglist.screens.main.currentLists.CurrentListsModule

/**
 * Defines fragments with its modules to allow Dagger to inject its dependencies
 */
@Module
abstract class FragmentBindingModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = [CurrentListsModule::class])
    internal abstract fun currentListsFragment(): CurrentListsFragment

}