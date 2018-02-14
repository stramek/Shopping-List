package pl.marcinstramowski.shoppinglist.database.sources

import io.reactivex.Completable
import io.reactivex.rxkotlin.subscribeBy
import pl.marcinstramowski.shoppinglist.database.dao.ShoppingListDao
import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.rxSchedulers.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingListRepo @Inject constructor(
    private val shoppingListDao: ShoppingListDao,
    private val schedulers: SchedulerProvider
) : ShoppingListDataSource {

    override fun observeArchivedListsWithItems() = shoppingListDao.getArchivedListsWithItems()

    override fun observeCurrentListsWithItems() = shoppingListDao.getCurrentListsWithItems()

    override fun observeShoppingListById(listId: Long) = shoppingListDao.getShoppingListById(listId)

    override fun observeShoppingItemsByListId(listId: Long) = shoppingListDao.getShoppingItemsByParentId(listId)

    override fun deleteShoppingList(shoppingList: ShoppingList) {
        Completable.fromAction { shoppingListDao.deleteShoppingListWithItems(shoppingList) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.io())
            .subscribeBy(
                onError = { Timber.e(it, "Error deleting shopping list!") }
            )
    }

    override fun archiveShoppingList(shoppingList: ShoppingList) {
        Completable.fromAction { shoppingListDao.archiveListRefreshTime(shoppingList) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.io())
            .subscribeBy(
                onError = { Timber.e(it, "Error archiving shopping list!") }
            )
    }

    override fun updateShoppingListName(shoppingList: ShoppingList, newName: String) {
        Completable.fromAction { shoppingListDao.updateShoppingListNameRefreshTime(shoppingList, newName) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.io())
            .subscribeBy(
                onError = { Timber.e(it, "Error when updating shopping list name!") }
            )
    }

    override fun insertOrUpdateShoppingItem(shoppingItem: ShoppingItem) {
        Completable.fromAction { shoppingListDao.insertOrUpdateRefreshTime(shoppingItem) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.io())
            .subscribeBy(
                onError = { Timber.e(it, "Error when inserting shopping item!") }
            )
    }

    override fun insertOrUpdateShoppingList(shoppingList: ShoppingList) {
        Completable.fromAction { shoppingListDao.insertOrUpdate(shoppingList) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.io())
            .subscribeBy(
                onError = { Timber.e(it, "Error when inserting shopping list!") }
            )
    }

    override fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        Completable.fromAction { shoppingListDao.deleteRefreshTime(shoppingItem) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.io())
            .subscribeBy(
                onError = { Timber.e(it, "Error when deleting shopping item!") }
            )
    }

    override fun setShoppingItemCompleted(shoppingItem: ShoppingItem, completed: Boolean) {
        Completable.fromAction {
            shoppingListDao.setShoppingItemAsCompletedUpdateTime(shoppingItem, completed)
        }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.io())
            .subscribeBy(
                onError = { Timber.e(it, "Error when changing shopping item state!") }
            )
    }

    override fun updateShoppingItemName(shoppingItem: ShoppingItem, newName: String) {
        Completable.fromAction {
            shoppingListDao.updateShoppingItemNameRefreshTime(shoppingItem, newName)
        }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.io())
            .subscribeBy(
                onError = { Timber.e(it, "Error when updating shopping item name!") }
            )
    }
}