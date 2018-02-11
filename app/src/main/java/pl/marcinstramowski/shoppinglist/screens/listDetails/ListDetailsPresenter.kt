package pl.marcinstramowski.shoppinglist.screens.listDetails

import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import pl.marcinstramowski.shoppinglist.database.AppDatabase
import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem
import pl.marcinstramowski.shoppinglist.rxSchedulers.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject

/**
 * List details screen logic
 */
class ListDetailsPresenter @Inject constructor(
    val view: ListDetailsContract.View,
    private val schedulers: SchedulerProvider,
    private val database: AppDatabase
) : ListDetailsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var shoppingListId: Long = -1

    override fun onAttach() {

    }

    override fun onDetach() {
        compositeDisposable.clear()
    }

    override fun setupTargetShoppingListId(shoppingListId: Long) {
        this.shoppingListId = shoppingListId
        startShoppingListUpdates(shoppingListId)
    }

    private fun startShoppingListUpdates(shoppingListId: Long) {
        this.shoppingListId = shoppingListId
        compositeDisposable.add(
            database.shoppingListDao().getShoppingListWithItemsById(shoppingListId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeBy(
                    onNext = { shoppingList ->
                        view.showShoppingListName(shoppingList.shoppingList!!.listName)
                        view.updateList(shoppingList.shoppingItems)
                    },
                    onError = { error -> Timber.e(error) }
                )
        )
    }

    override fun addShoppingItem(shoppingItemName: String) {
        if (shoppingItemName.isNotBlank()) {
            Completable.fromAction {
                database.shoppingListDao().insertOrUpdate(ShoppingItem(shoppingListId, shoppingItemName))
            }.subscribeOn(schedulers.io()).subscribe()
        }
    }

    override fun removeShoppingItem(shoppingItem: ShoppingItem) {
        Completable.fromAction {
            database.shoppingListDao().delete(shoppingItem)
        }.subscribeOn(schedulers.io()).subscribe()
    }

    override fun setShoppingItemCompleted(shoppingItem: ShoppingItem, completed: Boolean) {
        Completable.fromAction {
            database.shoppingListDao().setShoppingItemAsCompleted(shoppingItem.id!!, completed)
        }.subscribeOn(schedulers.io()).subscribe()
    }
}