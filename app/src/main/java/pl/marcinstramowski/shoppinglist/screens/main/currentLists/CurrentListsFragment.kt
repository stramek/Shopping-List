package pl.marcinstramowski.shoppinglist.screens.main.currentLists

import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.StableId
import kotlinx.android.synthetic.main.fragment_current_lists.*
import pl.marcinstramowski.shoppinglist.BR
import pl.marcinstramowski.shoppinglist.R
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.databinding.ViewShoppingListBinding
import pl.marcinstramowski.shoppinglist.screens.base.BaseFragment
import pl.marcinstramowski.shoppinglist.utils.GenericDiffCallback
import javax.inject.Inject

/**
 * Screen that allows user to manage his events
 */
class CurrentListsFragment : BaseFragment<CurrentListsContract.Presenter>(), CurrentListsContract.View {

    @Inject override lateinit var presenter: CurrentListsContract.Presenter
    override val contentViewId = R.layout.fragment_current_lists

    private lateinit var lastAdapter: LastAdapter
    private val adapterList = ArrayList<ShoppingListWithItems>()

    override fun onCreated(savedInstanceState: Bundle?) {
        addItem.setOnClickListener { presenter.onAddItemClick() }
        removeAll.setOnClickListener { presenter.onRemoveClick() }
        configureShoppingListAdapter()
    }

    private fun configureShoppingListAdapter() {
        list_container.layoutManager = LinearLayoutManager(context)
        list_container.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        lastAdapter = LastAdapter(adapterList, BR.item)
            .map<ShoppingListWithItems, ViewShoppingListBinding>(R.layout.view_shopping_list)
            .into(list_container)
    }

    override fun updateShoppingLists(shoppingLists: List<ShoppingListWithItems>) {
        val diffCallback = GenericDiffCallback(adapterList, shoppingLists)
        DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(lastAdapter)
        adapterList.apply { clear() }.addAll(shoppingLists)
        list_container.smoothScrollToPosition(0)
    }
}