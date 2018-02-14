package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.currentLists

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import pl.marcinstramowski.shoppinglist.database.sources.ShoppingListDataSource
import pl.marcinstramowski.shoppinglist.rxSchedulers.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject

/**
 * Current lists screen logic
 */
class CurrentListsPresenter @Inject constructor(
    private val view: CurrentListsContract.View,
    private val schedulers: SchedulerProvider,
    private val shoppingListSource: ShoppingListDataSource
) : CurrentListsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onAttach() {
        subscribeShoppingLists()
    }

    override fun onDetach() {
        compositeDisposable.clear()
    }

    private fun subscribeShoppingLists() {
        compositeDisposable.add(
            shoppingListSource.observeCurrentListsWithItems()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeBy(
                    onNext = { shoppingLists -> view.updateShoppingLists(shoppingLists) },
                    onError = { error -> Timber.e(error) }
                )
        )
    }

    override fun archiveList(shoppingList: ShoppingList?) {
        shoppingList?.let { shoppingListSource.archiveShoppingList(it) }
    }

    override fun editListName(shoppingList: ShoppingList?, newName: String) {
        shoppingList?.let { shoppingListSource.updateShoppingListName(shoppingList, newName) }
    }

    override fun onShoppingListClick(shoppingListWithItems: ShoppingListWithItems) {
        view.showListDetailsScreen(shoppingListWithItems.getUniqueId())
    }

    override fun onShoppingListLongClick(shoppingListWithItems: ShoppingListWithItems) {
        shoppingListWithItems.shoppingList?.let { view.showContextMenu(it) }
    }
}