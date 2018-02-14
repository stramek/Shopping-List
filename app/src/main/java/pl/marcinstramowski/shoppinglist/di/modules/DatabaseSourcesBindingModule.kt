package pl.marcinstramowski.shoppinglist.di.modules

import dagger.Binds
import dagger.Module
import pl.marcinstramowski.shoppinglist.database.sources.ShoppingListDataSource
import pl.marcinstramowski.shoppinglist.database.sources.ShoppingListRepo

@Module
interface DatabaseSourcesBindingModule {

    @Binds fun bindShoppingListSource(repo: ShoppingListRepo): ShoppingListDataSource

}