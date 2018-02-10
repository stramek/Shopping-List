package pl.marcinstramowski.shoppinglist.screens.main.currentLists

import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
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
        Completable.fromAction {
            database.shoppingListDao().insert(ShoppingList("TestTest ${System.currentTimeMillis()}"))
        }
            .subscribeOn(schedulers.io()).subscribe()

    }

    private fun subscribeShoppingLists() {
        compositeDisposable.add(database.shoppingListDao().getAll()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe({ shoppingLists ->
                Timber.e("Got ${shoppingLists.size} shopping lists!")
                view.updateShoppingLists(shoppingLists)
            }, { error -> Timber.e(error) })
        )
    }

    override fun onRemoveClick() {
        Completable.fromAction {
            database.shoppingListDao().deleteAll()
        }.subscribeOn(schedulers.io()).subscribe()
    }
}