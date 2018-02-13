package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.archivedLists

import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.screens.base.BaseContract

/**
 * Contract interfaces between [ArchivedListsFragment] and [ArchivedListsPresenter]
 */
interface ArchivedListsContract {

    interface View : BaseContract.View<Presenter> {

        fun updateShoppingLists(shoppingLists: List<ShoppingListWithItems>)

        fun showListDetailsScreen(shoppingListId: Long)

        fun showContextMenu(shoppingList: ShoppingList)

    }

    interface Presenter : BaseContract.Presenter {

        fun deleteList(shoppingList: ShoppingList?)

        fun onShoppingListClick(shoppingListWithItems: ShoppingListWithItems)

        fun onLongShoppingListClick(shoppingListWithItems: ShoppingListWithItems)
    }
}