package pl.marcinstramowski.shoppinglist.screens.main.currentLists

import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.screens.base.BaseContract

/**
 * Contract interfaces between [CurrentListsFragment] and [CurrentListsPresenter]
 */
interface CurrentListsContract {

    interface View : BaseContract.View<Presenter> {
        fun updateShoppingLists(shoppingLists: List<ShoppingList>)
    }

    interface Presenter : BaseContract.Presenter {
        fun onAddItemClick()
        fun onRemoveClick()
    }
}