package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.currentLists

import android.support.v7.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import org.jetbrains.anko.support.v4.startActivity
import pl.marcinstramowski.shoppinglist.R
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.extensions.showTextInputDialog
import pl.marcinstramowski.shoppinglist.screens.listDetails.ListDetailsActivity
import pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.BaseListFragment
import javax.inject.Inject

/**
 * Screen with current lists
 */
class CurrentListsFragment : BaseListFragment<CurrentListsContract.Presenter>(),
    CurrentListsContract.View, ActionMode.Callback  {

    @Inject override lateinit var presenter: CurrentListsContract.Presenter

    override val emptyListMessageRes = R.string.current_notes_empty_message

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
        presenter.onShoppingListLongClick(shoppingListWithItems)
    }

    override fun showContextMenu(shoppingList: ShoppingList) {
        actionMode = getCompatActivity().startSupportActionMode(this)
        selectedShoppingList = shoppingList
    }

    override fun showListDetailsScreen(shoppingListId: Long) {
        startActivity<ListDetailsActivity>(
            ListDetailsActivity.SHOPPING_LIST_ID_KEY to shoppingListId
        )
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_archive -> {
                presenter.archiveList(selectedShoppingList)
                mode.finish()
                true
            }
            R.id.menu_edit -> {
                activity?.showTextInputDialog(R.string.dialog_change_list_name, { newName ->
                    presenter.editListName(selectedShoppingList, newName)
                })
                mode.finish()
                true
            }
            else -> false
        }
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu?): Boolean {
        val inflater = mode.menuInflater
        inflater.inflate(R.menu.current_list_context_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
    }
}