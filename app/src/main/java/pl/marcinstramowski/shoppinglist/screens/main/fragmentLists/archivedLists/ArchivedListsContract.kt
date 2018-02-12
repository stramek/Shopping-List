package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.archivedLists

import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.screens.base.BaseContract

/**
 * Contract interfaces between [ArchivedListsFragment] and [ArchivedListsPresenter]
 */
interface ArchivedListsContract {

    interface View : BaseContract.View<Presenter> {

        fun updateShoppingLists(shoppingLists: List<ShoppingListWithItems>)

        fun showListDetailsScreen(shoppingListId: Long)

    }

    interface Presenter : BaseContract.Presenter {

        fun deleteList(shoppingListWithItems: ShoppingListWithItems)

        fun onShoppingListClick(shoppingListWithItems: ShoppingListWithItems)

        fun onLongShoppingListClock(shoppingListWithItems: ShoppingListWithItems)
    }
}