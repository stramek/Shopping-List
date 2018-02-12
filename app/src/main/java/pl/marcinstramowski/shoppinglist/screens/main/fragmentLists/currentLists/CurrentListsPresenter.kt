package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.currentLists

import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import pl.marcinstramowski.shoppinglist.database.AppDatabase
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
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

    private fun subscribeShoppingLists() {
        compositeDisposable.add(
            database.shoppingListDao().getCurrentListsWithItems()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeBy(
                    onNext = { shoppingLists -> view.updateShoppingLists(shoppingLists) },
                    onError = { error -> Timber.e(error) }
                )
        )
    }

    override fun archiveList(shoppingListWithItems: ShoppingListWithItems) {
        Completable
            .fromAction { database.shoppingListDao().archiveShoppingList(shoppingListWithItems.getUniqueId()) }
            .subscribeOn(schedulers.io())
            .subscribe()
    }

    override fun onShoppingListClick(shoppingListWithItems: ShoppingListWithItems) {
        shoppingListWithItems.shoppingList?.id?.let {
            view.showListDetailsScreen(it)
        }
    }

    override fun onLongShoppingListClock(shoppingListWithItems: ShoppingListWithItems) {

    }
}