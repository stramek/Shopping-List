package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.currentLists

import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.BaseListFragment
import javax.inject.Inject

/**
 * Screen with current lists
 */
class CurrentListsFragment : BaseListFragment<CurrentListsContract.Presenter>(), CurrentListsContract.View {

    @Inject override lateinit var presenter: CurrentListsContract.Presenter

    override fun updateShoppingLists(shoppingLists: List<ShoppingListWithItems>) {
        updateList(shoppingLists)
    }

    override fun onItemClick(shoppingListWithItems: ShoppingListWithItems) {
        presenter.archiveList(shoppingListWithItems)
    }
}