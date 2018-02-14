package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.currentLists

import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.screens.base.BaseContract

/**
 * Contract interfaces between [CurrentListsFragment] and [CurrentListsPresenter]
 */
interface CurrentListsContract {

    interface View : BaseContract.View<Presenter> {

        fun updateShoppingLists(shoppingLists: List<ShoppingListWithItems>)

        fun showListDetailsScreen(shoppingListId: Long)

        fun showContextMenu(shoppingList: ShoppingList)

    }

    interface Presenter : BaseContract.Presenter {

        fun archiveList(shoppingList: ShoppingList?)

        fun editListName(shoppingList: ShoppingList?, newName: String)

        fun onShoppingListClick(shoppingListWithItems: ShoppingListWithItems)

        fun onShoppingListLongClick(shoppingListWithItems: ShoppingListWithItems)

    }
}