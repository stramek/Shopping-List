package pl.marcinstramowski.shoppinglist.screens.listDetails

import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
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
import pl.marcinstramowski.shoppinglist.screens.base.BaseActivity
import pl.marcinstramowski.shoppinglist.utils.GenericDiffCallback
import javax.inject.Inject


class ListDetailsActivity : BaseActivity<ListDetailsContract.Presenter>(),
    ListDetailsContract.View, TextView.OnEditorActionListener {

    companion object {
        const val SHOPPING_LIST_ID_KEY = "SHOPPING_LIST_ID_KEY"
        const val SHOPPING_LIST_EDITABLE_KEY = "SHOPPING_LIST_EDITABLE_KEY"
    }

    @Inject override lateinit var presenter: ListDetailsContract.Presenter

    override val contentViewId = R.layout.activity_list_details

    private lateinit var lastAdapter: LastAdapter
    private val adapterList = ArrayList<ShoppingItem>()

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

    private fun configureShoppingListAdapter() {
        listContainer.layoutManager = LinearLayoutManager(this)
        listContainer.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        lastAdapter = LastAdapter(adapterList, BR.item)
            .map<ShoppingItem, ItemShoppingItemBinding>(R.layout.item_shopping_item) {
                onBind {
                    configureShoppingItem(it.binding.removeButton, it.binding.root, it.binding.item)
                }
            }
            .into(listContainer)
    }

    private fun configureShoppingItem(removeButton: ImageView, root: View, shoppingItem: ShoppingItem?) {
        removeButton.setVisible(isEditable)
        if (shoppingItem == null || !isEditable) return
        removeButton.setOnClickListener { presenter.removeShoppingItem(shoppingItem) }
        root.setOnClickListener { presenter.changeShoppingListCompletedState(shoppingItem) }
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
}