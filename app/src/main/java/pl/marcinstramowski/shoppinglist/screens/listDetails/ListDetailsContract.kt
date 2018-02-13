package pl.marcinstramowski.shoppinglist.screens.listDetails

import com.jakewharton.rxbinding2.InitialValueObservable
import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem
import pl.marcinstramowski.shoppinglist.screens.base.BaseContract

/**
 * Contract interfaces between [ListDetailsActivity] and [ListDetailsPresenter]
 */
interface ListDetailsContract {

    interface View : BaseContract.View<Presenter> {

        fun updateList(shoppingLists: List<ShoppingItem>)

        fun showShoppingListName(name: String)

        fun cleanAddNewItemField()

        fun setAddNewItemVisible(visible: Boolean)

        fun showChangeItemNameDialog(shoppingItem: ShoppingItem)

        fun showContextMenu(shoppingItem: ShoppingItem)

    }

    interface Presenter : BaseContract.Presenter {

        fun observeShoppingListId(shoppingListId: Long)

        fun addShoppingItem(shoppingListId: Long, shoppingItemName: String)

        fun removeShoppingItem(shoppingItem: ShoppingItem)

        fun changeShoppingListCompletedState(shoppingItem: ShoppingItem)

        fun changeShoppingItemName(shoppingItem: ShoppingItem?, newName: String)

        fun observeAddNewItemEditText(observable: InitialValueObservable<CharSequence>)

        fun onLongShoppingItemClick(shoppingItem: ShoppingItem)

    }

}