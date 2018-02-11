package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.archivedLists

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import pl.marcinstramowski.shoppinglist.database.AppDatabase
import pl.marcinstramowski.shoppinglist.rxSchedulers.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject

/**
 * Archived lists screen logic
 */
class ArchivedListsPresenter @Inject constructor(
    private val view: ArchivedListsContract.View,
    private val schedulers: SchedulerProvider,
    private val database: AppDatabase
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
            database.shoppingListDao().getArchivedListsWithItems()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeBy(
                    onNext = { shoppingLists -> view.updateShoppingLists(shoppingLists) },
                    onError = { error -> Timber.e(error) }
                )
        )
    }
}