package pl.marcinstramowski.shoppinglist.screens.main

import io.reactivex.Completable
import pl.marcinstramowski.shoppinglist.database.AppDatabase
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.rxSchedulers.SchedulerProvider
import javax.inject.Inject

/**
 * MainActivity login
 */
class MainPresenter @Inject constructor(
    val view: MainContract.View,
    private val schedulers: SchedulerProvider,
    private val database: AppDatabase
) : MainContract.Presenter {

    override fun onAttach() {

    }

    override fun onDetach() {

    }

    override fun onFabButtonClick() {
        view.showAddNewListDialog()
    }

    override fun createNewList(listName: String) {
        Completable.fromAction {
            database.shoppingListDao().insertOrUpdate(ShoppingList(listName))
        }.subscribeOn(schedulers.io()).subscribe()
    }
}