package pl.marcinstramowski.shoppinglist.screens.listDetails

import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem
import pl.marcinstramowski.shoppinglist.screens.base.BaseContract

/**
 * Contract interfaces between [ListDetailsActivity] and [ListDetailsPresenter]
 */
interface ListDetailsContract {

    interface View : BaseContract.View<Presenter> {
        fun updateList(shoppingLists: List<ShoppingItem>)
        fun showShoppingListName(name: String)
    }

    interface Presenter : BaseContract.Presenter {
        fun setupTargetShoppingListId(shoppingListId: Long)
        fun addShoppingItem(shoppingItemName: String)
        fun removeShoppingItem(shoppingItem: ShoppingItem)
        fun setShoppingItemCompleted(shoppingItem: ShoppingItem, completed: Boolean)
    }

}