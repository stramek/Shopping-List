package pl.marcinstramowski.shoppinglist.screens.main

import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.sources.ShoppingListDataSource
import javax.inject.Inject

/**
 * MainActivity login
 */
class MainPresenter @Inject constructor(
    val view: MainContract.View,
    private val shoppingListSource: ShoppingListDataSource
) : MainContract.Presenter {

    override fun onAttach() {

    }

    override fun onDetach() {

    }

    override fun onFabButtonClick() {
        view.showAddNewListDialog()
    }

    override fun createNewList(listName: String) {
        shoppingListSource.insertOrUpdateShoppingList(ShoppingList(listName))
    }
}