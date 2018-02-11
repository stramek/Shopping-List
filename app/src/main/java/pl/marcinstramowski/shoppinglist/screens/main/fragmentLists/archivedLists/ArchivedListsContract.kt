package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.archivedLists

import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.screens.base.BaseContract

/**
 * Contract interfaces between [ArchivedListsFragment] and [ArchivedListsPresenter]
 */
interface ArchivedListsContract {

    interface View : BaseContract.View<Presenter> {
        fun updateShoppingLists(shoppingLists: List<ShoppingListWithItems>)
    }

    interface Presenter : BaseContract.Presenter
}