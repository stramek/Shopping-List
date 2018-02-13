package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.archivedLists

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.database.sources.ShoppingListDataSource
import pl.marcinstramowski.shoppinglist.rxSchedulers.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject

/**
 * Archived lists screen logic
 */
class ArchivedListsPresenter @Inject constructor(
    private val view: ArchivedListsContract.View,
    private val schedulers: SchedulerProvider,
    private val shoppingListSource: ShoppingListDataSource
) : ArchivedListsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onAttach() {
        subscribeShoppingLists()
    }

    override fun onDetach() {
        compositeDisposable.clear()
    }

    private fun subscribeShoppingLists() {
        compositeDisposable.add(
            shoppingListSource.observeArchivedListsWithItems()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeBy(
                    onNext = { shoppingLists -> view.updateShoppingLists(shoppingLists) },
                    onError = { error -> Timber.e(error) }
                )
        )
    }

    override fun deleteList(shoppingList: ShoppingList?) {
        shoppingList?.let { shoppingListSource.deleteShoppingList(shoppingList )}
    }

    override fun onShoppingListClick(shoppingListWithItems: ShoppingListWithItems) {
        view.showListDetailsScreen(shoppingListWithItems.getUniqueId())
    }

    override fun onLongShoppingListClick(shoppingListWithItems: ShoppingListWithItems) {
        shoppingListWithItems.shoppingList?.let {
            view.showContextMenu(it)
        }
    }
}