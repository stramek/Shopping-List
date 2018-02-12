package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.archivedLists

import org.jetbrains.anko.support.v4.startActivity
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.screens.listDetails.ListDetailsActivity
import pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.BaseListFragment
import javax.inject.Inject

/**
 * Screen with archived lists
 */
class ArchivedListsFragment : BaseListFragment<ArchivedListsContract.Presenter>(),
    ArchivedListsContract.View {

    @Inject override lateinit var presenter: ArchivedListsContract.Presenter

    override fun updateShoppingLists(shoppingLists: List<ShoppingListWithItems>) {
        updateList(shoppingLists)
    }

    override fun onItemClick(shoppingListWithItems: ShoppingListWithItems) {
        presenter.onShoppingListClick(shoppingListWithItems)
    }

    override fun onLongItemClick(shoppingListWithItems: ShoppingListWithItems) {
        presenter.onLongShoppingListClick(shoppingListWithItems)
    }

    override fun showListDetailsScreen(shoppingListId: Long) {
        startActivity<ListDetailsActivity>(
            ListDetailsActivity.SHOPPING_LIST_ID_KEY to shoppingListId,
            ListDetailsActivity.SHOPPING_LIST_EDITABLE_KEY to false
        )
    }
}