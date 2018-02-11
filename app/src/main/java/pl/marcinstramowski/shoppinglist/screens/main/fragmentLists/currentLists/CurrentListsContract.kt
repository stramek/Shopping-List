package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.currentLists

import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.screens.base.BaseContract

/**
 * Contract interfaces between [CurrentListsFragment] and [CurrentListsPresenter]
 */
interface CurrentListsContract {

    interface View : BaseContract.View<Presenter> {
        fun updateShoppingLists(shoppingLists: List<ShoppingListWithItems>)
    }

    interface Presenter : BaseContract.Presenter {
        fun archiveList(shoppingListWithItems: ShoppingListWithItems)
    }
}