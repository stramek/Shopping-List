package pl.marcinstramowski.shoppinglist.screens.listDetails

import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.view.ActionMode
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import com.github.nitrico.lastadapter.LastAdapter
import com.jakewharton.rxbinding2.widget.textChanges
import kotlinx.android.synthetic.main.activity_list_details.*
import pl.marcinstramowski.shoppinglist.BR
import pl.marcinstramowski.shoppinglist.R
import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem
import pl.marcinstramowski.shoppinglist.databinding.ItemShoppingItemBinding
import pl.marcinstramowski.shoppinglist.extensions.clear
import pl.marcinstramowski.shoppinglist.extensions.setVisible
import pl.marcinstramowski.shoppinglist.extensions.showTextInputDialog
import pl.marcinstramowski.shoppinglist.screens.base.BaseActivity
import pl.marcinstramowski.shoppinglist.utils.GenericDiffCallback
import javax.inject.Inject


class ListDetailsActivity : BaseActivity<ListDetailsContract.Presenter>(),
    ListDetailsContract.View, TextView.OnEditorActionListener, ActionMode.Callback {

    companion object {
        const val SHOPPING_LIST_ID_KEY = "SHOPPING_LIST_ID_KEY"
        const val SHOPPING_LIST_EDITABLE_KEY = "SHOPPING_LIST_EDITABLE_KEY"
    }

    @Inject override lateinit var presenter: ListDetailsContract.Presenter

    override val contentViewId = R.layout.activity_list_details

    private var actionMode: ActionMode? = null
    private var selectedShoppingItem: ShoppingItem? = null

    private lateinit var lastAdapter: LastAdapter
    private val adapterList = mutableListOf<ShoppingItem>()

    private var isEditable: Boolean = true
    private var shoppingListId: Long = -1

    override fun onCreated(savedInstanceState: Bundle?) {
        isEditable = intent.getBooleanExtra(SHOPPING_LIST_EDITABLE_KEY, true)
        shoppingListId = intent.getLongExtra(SHOPPING_LIST_ID_KEY, -1)

        if (!isEditable) diasbleAddItemSection()

        configureShoppingListAdapter()
        productEditText.setOnEditorActionListener(this)

        addButton.setOnClickListener {
            presenter.addShoppingItem(shoppingListId, productEditText.text.toString())
        }
    }

    private fun configureShoppingListAdapter() {
        listContainer.layoutManager = LinearLayoutManager(this)
        listContainer.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        lastAdapter = LastAdapter(adapterList, BR.item)
            .map<ShoppingItem, ItemShoppingItemBinding>(R.layout.item_shopping_item) {
                onBind {
                    val removeButton = it.binding.removeButton
                    val root = it.binding.root
                    val item = it.binding.item
                    item?.let { configureShoppingItem(removeButton, root, item) }
                }
            }
            .into(listContainer)
    }

    private fun configureShoppingItem(removeButton: ImageView, root: View, shoppingItem: ShoppingItem) {
        removeButton.setVisible(isEditable)
        if (!isEditable) return
        removeButton.setOnClickListener { presenter.removeShoppingItem(shoppingItem) }
        root.setOnClickListener {
            if (actionMode != null) {
                actionMode?.finish()
            } else {
                presenter.changeShoppingListCompletedState(shoppingItem)
            }
        }
        root.setOnLongClickListener {
            presenter.onLongShoppingItemClick(shoppingItem)
            true
        }
    }

    override fun showContextMenu(shoppingItem: ShoppingItem) {
        actionMode = startSupportActionMode(this)
        selectedShoppingItem = shoppingItem
    }

    override fun onStart() {
        super.onStart()
        presenter.observeAddNewItemEditText(productEditText.textChanges())
        presenter.observeShoppingListId(shoppingListId)
    }

    override fun onEditorAction(view: TextView, action: Int, event: KeyEvent?): Boolean {
        val inputText = view.text.toString().trim()
        if (action == EditorInfo.IME_ACTION_DONE && inputText.isNotBlank()) {
            presenter.addShoppingItem(shoppingListId, inputText)
            return true
        }
        return false
    }

    override fun setAddNewItemVisible(visible: Boolean) {
        addButton.setVisible(visible)
    }

    override fun cleanAddNewItemField() {
        productEditText.clear()
    }

    override fun showShoppingListName(name: String) {
        supportActionBar?.title = name
    }

    override fun updateList(shoppingLists: List<ShoppingItem>) {
        listContainer.visibility = View.VISIBLE
        val diffCallback = GenericDiffCallback(adapterList, shoppingLists)
        DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(lastAdapter)
        adapterList.apply { clear() }.addAll(shoppingLists)
    }

    private fun diasbleAddItemSection() {
        addItemSection.visibility = View.GONE
    }

    override fun showChangeItemNameDialog(shoppingItem: ShoppingItem) {
        showTextInputDialog(
            R.string.dialog_change_item_name, { text ->
                presenter.changeShoppingItemName(shoppingItem, text)
            }
        )
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_edit -> {
                showTextInputDialog(R.string.dialog_change_list_name, { newName ->
                    presenter.changeShoppingItemName(selectedShoppingItem, newName)
                })
                mode.finish()
                true
            }
            else -> false
        }
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu?): Boolean {
        val inflater = mode.menuInflater
        inflater.inflate(R.menu.details_list_context_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
    }
}