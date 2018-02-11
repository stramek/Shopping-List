package pl.marcinstramowski.shoppinglist.screens.main

import pl.marcinstramowski.shoppinglist.database.AppDatabase
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
        /*Completable.fromAction {
            val listId = database.shoppingListDao().insertOrUpdate(ShoppingList("Shopping list name"))
            database.shoppingListDao().insertOrUpdate(
                listOf(
                    ShoppingItem(listId, "Item name1").apply { isCompleted = Random().nextBoolean() },
                    ShoppingItem(listId, "Item name2").apply { isCompleted = Random().nextBoolean() },
                    ShoppingItem(listId, "Item name3").apply { isCompleted = Random().nextBoolean() },
                    ShoppingItem(listId, "Item name4").apply { isCompleted = Random().nextBoolean() },
                    ShoppingItem(listId, "Item name5").apply { isCompleted = Random().nextBoolean() }
                )
            )
        }
            .subscribeOn(schedulers.io())
            .subscribe()*/
        view.showAddNewListActivity()
    }
}