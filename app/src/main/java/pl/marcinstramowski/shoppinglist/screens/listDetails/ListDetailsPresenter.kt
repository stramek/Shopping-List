package pl.marcinstramowski.shoppinglist.screens.listDetails

import com.jakewharton.rxbinding2.InitialValueObservable
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
            database.shoppingListDao().getShoppingListById(shoppingListId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeBy(
                    onNext = { shoppingList ->
                        view.showShoppingListName(shoppingList.listName)
                    },
                    onError = { error -> Timber.e(error) }
                )
        )
    }

    private fun observeShoppingItems(shoppingListId: Long) {
        compositeDisposable.add(
            database.shoppingListDao().getShoppingItemsByParentId(shoppingListId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeBy(
                    onNext = { shoppingItems ->
                        view.updateList(shoppingItems)
                    },
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
            Completable.fromAction {
                database.shoppingListDao().insertOrUpdateRefreshTime(
                    ShoppingItem(shoppingListId, shoppingItemName.capitalize())
                )
            }.subscribeOn(schedulers.io()).subscribe()
        }
    }

    override fun removeShoppingItem(shoppingItem: ShoppingItem) {
        Completable.fromAction {
            database.shoppingListDao().deleteRefreshTime(shoppingItem)
        }.subscribeOn(schedulers.io()).subscribe()
    }

    override fun changeShoppingListCompletedState(shoppingItem: ShoppingItem) {
        Completable.fromAction {
            database.shoppingListDao().setShoppingItemAsCompletedUpdateTime(shoppingItem, !shoppingItem.isCompleted)
        }.subscribeOn(schedulers.io()).subscribe()
    }

    override fun changeShoppingItemName(shoppingItem: ShoppingItem, newName: String) {
        Completable.fromAction {

        }
    }
}