package pl.marcinstramowski.shoppinglist.screens.listDetails

import com.jakewharton.rxbinding2.InitialValueObservable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem
import pl.marcinstramowski.shoppinglist.database.sources.ShoppingListDataSource
import pl.marcinstramowski.shoppinglist.rxSchedulers.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject

/**
 * List details screen logic
 */
class ListDetailsPresenter @Inject constructor(
    val view: ListDetailsContract.View,
    private val schedulers: SchedulerProvider,
    private val shoppingListSource: ShoppingListDataSource
) : ListDetailsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onAttach() {}

    override fun onDetach() {
        compositeDisposable.clear()
    }

    override fun observeShoppingListId(shoppingListId: Long) {
        observeShoppingList(shoppingListId)
        observeShoppingItems(shoppingListId)
    }

    private fun observeShoppingList(shoppingListId: Long) {
        compositeDisposable.add(
            shoppingListSource.observeShoppingListById(shoppingListId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeBy(
                    onNext = { shoppingList -> view.showShoppingListName(shoppingList.listName) },
                    onError = { error -> Timber.e(error) }
                )
        )
    }

    private fun observeShoppingItems(shoppingListId: Long) {
        compositeDisposable.add(
            shoppingListSource.observeShoppingItemsByListId(shoppingListId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeBy(
                    onNext = { shoppingItems -> view.updateList(shoppingItems) },
                    onError = { error -> Timber.e(error) }
                )
        )
    }

    override fun observeAddNewItemEditText(observable: InitialValueObservable<CharSequence>) {
        compositeDisposable.add(
            observable.subscribeBy { view.setAddNewItemVisible(it.isNotBlank()) }
        )
    }

    override fun addShoppingItem(shoppingListId: Long, shoppingItemName: String) {
        view.cleanAddNewItemField()
        if (shoppingItemName.isNotBlank()) {
            shoppingListSource.insertOrUpdateShoppingItem(
                ShoppingItem(shoppingListId, shoppingItemName.capitalize())
            )
        }
    }

    override fun removeShoppingItem(shoppingItem: ShoppingItem) {
        shoppingListSource.deleteShoppingItem(shoppingItem)
    }

    override fun changeShoppingListCompletedState(shoppingItem: ShoppingItem) {
        shoppingListSource.setShoppingItemCompleted(shoppingItem, !shoppingItem.isCompleted)
    }

    override fun changeShoppingItemName(shoppingItem: ShoppingItem?, newName: String) {
        shoppingItem?.let { shoppingListSource.updateShoppingItemName(it, newName) }
    }

    override fun onLongShoppingItemClick(shoppingItem: ShoppingItem) {
        view.showContextMenu(shoppingItem)
    }
}