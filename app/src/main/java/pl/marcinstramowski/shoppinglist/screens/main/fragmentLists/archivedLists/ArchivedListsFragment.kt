package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.archivedLists

import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
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

    }
}