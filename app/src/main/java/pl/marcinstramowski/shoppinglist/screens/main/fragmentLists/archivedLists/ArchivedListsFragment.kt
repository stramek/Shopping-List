package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.archivedLists

import android.support.v7.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import org.jetbrains.anko.support.v4.startActivity
import pl.marcinstramowski.shoppinglist.R
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.screens.listDetails.ListDetailsActivity
import pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.BaseListFragment
import javax.inject.Inject

/**
 * Screen with archived lists
 */
class ArchivedListsFragment : BaseListFragment<ArchivedListsContract.Presenter>(),
    ArchivedListsContract.View, ActionMode.Callback {

    @Inject override lateinit var presenter: ArchivedListsContract.Presenter

    override val emptyListMessageRes = R.string.archived_notes_empty_message

    private var actionMode: ActionMode? = null
    private var selectedShoppingList: ShoppingList? = null

    override fun updateShoppingLists(shoppingLists: List<ShoppingListWithItems>) {
        updateList(shoppingLists)
    }

    override fun onItemClick(shoppingListWithItems: ShoppingListWithItems) {
        if (actionMode != null) {
            actionMode?.finish()
        } else {
            presenter.onShoppingListClick(shoppingListWithItems)
        }
    }

    override fun onLongItemClick(shoppingListWithItems: ShoppingListWithItems) {
        presenter.onLongShoppingListClick(shoppingListWithItems)
    }

    override fun showContextMenu(shoppingList: ShoppingList) {
        actionMode = getCompatActivity().startSupportActionMode(this)
        selectedShoppingList = shoppingList
    }

    override fun showListDetailsScreen(shoppingListId: Long) {
        startActivity<ListDetailsActivity>(
            ListDetailsActivity.SHOPPING_LIST_ID_KEY to shoppingListId,
            ListDetailsActivity.SHOPPING_LIST_EDITABLE_KEY to false
        )
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
                presenter.deleteList(selectedShoppingList)
                mode.finish()
                true
            }
            else -> false
        }
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu?): Boolean {
        val inflater = mode.menuInflater
        inflater.inflate(R.menu.archived_list_context_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
    }
}