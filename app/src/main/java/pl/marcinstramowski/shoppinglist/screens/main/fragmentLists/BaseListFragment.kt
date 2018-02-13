package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import pl.marcinstramowski.shoppinglist.BR
import pl.marcinstramowski.shoppinglist.R
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.databinding.ItemShoppingListBinding
import pl.marcinstramowski.shoppinglist.extensions.setVisible
import pl.marcinstramowski.shoppinglist.screens.base.BaseContract
import pl.marcinstramowski.shoppinglist.screens.base.BaseFragment
import pl.marcinstramowski.shoppinglist.utils.GenericDiffCallback
import pl.marcinstramowski.shoppinglist.utils.UniqueId

/**
 * Base list fragment
 */
abstract class BaseListFragment<out T : BaseContract.Presenter> : BaseFragment<T>(),
    BaseContract.View<T> {

    override val contentViewId = R.layout.fragment_list
    abstract val emptyListMessageRes: Int

    private lateinit var lastAdapter: LastAdapter
    private val adapterList = ArrayList<UniqueId>()

    @CallSuper
    override fun onCreated(savedInstanceState: Bundle?) {
        emptyListMessage.text = getText(emptyListMessageRes)
        configureShoppingListAdapter()
    }

    private fun configureShoppingListAdapter() {
        listContainer.layoutManager = LinearLayoutManager(context)
        listContainer.addItemDecoration(RecyclerItemDecorator(context!!))
        lastAdapter = LastAdapter(adapterList, BR.item)
            .map<ShoppingListWithItems, ItemShoppingListBinding>(R.layout.item_shopping_list) {
                onClick { it.binding.item?.let { onItemClick(it) } }
                onLongClick { it.binding.item?.let { onLongItemClick(it) } }
            }
            .into(listContainer)
    }

    abstract fun onItemClick(shoppingListWithItems: ShoppingListWithItems)

    abstract fun onLongItemClick(shoppingListWithItems: ShoppingListWithItems)

    @CallSuper
    fun updateList(shoppingLists: List<ShoppingListWithItems>) {
        listContainer.setVisible(true)
        emptyListMessage.setVisible(shoppingLists.isEmpty())
        val diffCallback = GenericDiffCallback(adapterList, shoppingLists)
        DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(lastAdapter)
        adapterList.apply { clear() }.addAll(shoppingLists)
        listContainer.scrollToPosition(0)
    }
}