package pl.marcinstramowski.shoppinglist.screens.listDetails

import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.activity_list_details.*
import kotlinx.android.synthetic.main.item_shopping_item.view.*
import pl.marcinstramowski.shoppinglist.BR
import pl.marcinstramowski.shoppinglist.R
import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem
import pl.marcinstramowski.shoppinglist.databinding.ItemShoppingItemBinding
import pl.marcinstramowski.shoppinglist.screens.base.BaseActivity
import pl.marcinstramowski.shoppinglist.utils.GenericDiffCallback
import timber.log.Timber
import javax.inject.Inject



class ListDetailsActivity : BaseActivity<ListDetailsContract.Presenter>(), ListDetailsContract.View {

    companion object {
        const val SHOPPING_LIST_ID_KEY = "SHOPPING_LIST_ID_KEY"
    }

    @Inject override lateinit var presenter: ListDetailsContract.Presenter

    override val contentViewId = R.layout.activity_list_details

    private lateinit var lastAdapter: LastAdapter
    private val adapterList = ArrayList<ShoppingItem>()

    override fun onCreated(savedInstanceState: Bundle?) {
        configureShoppingListAdapter()
        presenter.setupTargetShoppingListId(intent.getLongExtra(SHOPPING_LIST_ID_KEY, -1))
        addButton.setOnClickListener {addItem(productEditText.text.toString()) }
        productEditText.setOnEditorActionListener { textView, i, keyEvent ->
            Timber.e("text: ${textView.text} i: $i")
            var handled = false
            if (i == EditorInfo.IME_ACTION_DONE) {
                addItem(textView.text.toString())
                handled = true
            }
            handled
        }
    }

    private fun addItem(itemName: String) {
        presenter.addShoppingItem(itemName)
        productEditText.setText("")
    }

    override fun showShoppingListName(name: String) {
        Timber.e("setting name: $name")
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
                    val removeButton = it.binding.removeButton
                    val checkBox = it.binding.root.taskCompleted
                    val item = it.binding.item
                    item?.let {
                        removeButton.setOnClickListener { presenter.removeShoppingItem(item) }
                        checkBox.setOnCheckedChangeListener { button, completed ->
                            presenter.setShoppingItemCompleted(item, completed)
                        }
                    }
                }
            }
            .into(listContainer)
    }

    override fun updateList(shoppingLists: List<ShoppingItem>) {
        listContainer.visibility = View.VISIBLE
        val diffCallback = GenericDiffCallback(adapterList, shoppingLists)
        DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(lastAdapter)
        adapterList.apply { clear() }.addAll(shoppingLists)
    }
}