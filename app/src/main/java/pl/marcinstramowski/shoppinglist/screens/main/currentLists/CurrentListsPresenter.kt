package pl.marcinstramowski.shoppinglist.screens.main.currentLists

import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import pl.marcinstramowski.shoppinglist.database.AppDatabase
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.rxSchedulers.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject

/**
 * Current lists screen logic
 */
class CurrentListsPresenter @Inject constructor(
    private val view: CurrentListsContract.View,
    private val schedulers: SchedulerProvider,
    private val database: AppDatabase
) : CurrentListsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onAttach() {
        subscribeShoppingLists()
    }

    override fun onDetach() {
        compositeDisposable.clear()
    }

    override fun onAddItemClick() {
        val item = ShoppingList("lol")

//      val item = ShoppingItem(500, "Test123")

        Completable
            .fromAction {
                database.shoppingListDao().insert(item)
            }
            .subscribeOn(schedulers.io())
            .subscribe()
}

    private fun subscribeShoppingLists() {
        compositeDisposable.add(
            database.shoppingListDao().getAllWithItems()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeBy(
                    onNext = { shoppingLists -> view.updateShoppingLists(shoppingLists) },
                    onError = { error -> Timber.e(error) }
                )
        )
    }

    override fun onRemoveClick() {
        Completable
            .fromAction {
                database.shoppingListDao().deleteAll()
//                database.shoppingListDao().deleteShoppingListItems(500)
            }
            .subscribeOn(schedulers.io())
            .subscribe()
    }
}