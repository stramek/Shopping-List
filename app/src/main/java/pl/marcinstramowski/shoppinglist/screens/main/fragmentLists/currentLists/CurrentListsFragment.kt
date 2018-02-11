package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.currentLists

import org.jetbrains.anko.support.v4.startActivity
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.screens.listDetails.ListDetailsActivity
import pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.BaseListFragment
import timber.log.Timber
import javax.inject.Inject

/**
 * Screen with current lists
 */
class CurrentListsFragment : BaseListFragment<CurrentListsContract.Presenter>(), CurrentListsContract.View {

    @Inject override lateinit var presenter: CurrentListsContract.Presenter

    override fun updateShoppingLists(shoppingLists: List<ShoppingListWithItems>) {
        Timber.e("update came!")
        updateList(shoppingLists)
    }

    override fun onItemClick(shoppingListWithItems: ShoppingListWithItems) {
        shoppingListWithItems.shoppingList?.id?.let {
            startActivity<ListDetailsActivity>(ListDetailsActivity.SHOPPING_LIST_ID_KEY to it)
        }
    }

    override fun onLongItemClick(shoppingListWithItems: ShoppingListWithItems) {
        presenter.archiveList(shoppingListWithItems)
    }
}