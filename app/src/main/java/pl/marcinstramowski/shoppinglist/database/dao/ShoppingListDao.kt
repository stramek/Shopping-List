package pl.marcinstramowski.shoppinglist.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import pl.marcinstramowski.shoppinglist.database.converters.DateConverter
import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems
import java.util.*

@Dao
@TypeConverters(DateConverter::class)
abstract class ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrUpdate(shoppingList: ShoppingList): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrUpdate(shoppingItem: ShoppingItem): Long


    @Delete
    abstract fun delete(vararg shoppingLists: ShoppingList)

    @Delete
    abstract fun delete(vararg shoppingItems: ShoppingItem)

    @Query("DELETE FROM shoppingitem WHERE shoppingListId = :shoppingListId")
    abstract fun deleteShoppingListItems(shoppingListId: Long)


    @Transaction
    @Query("SELECT * FROM shoppingList WHERE archived = 0 ORDER BY lastModificationDate DESC")
    abstract fun getCurrentListsWithItems(): Flowable<List<ShoppingListWithItems>>

    @Transaction
    @Query("SELECT * FROM shoppingList WHERE archived = 1 ORDER BY lastModificationDate DESC")
    abstract fun getArchivedListsWithItems(): Flowable<List<ShoppingListWithItems>>

    @Transaction
    @Query("SELECT * FROM shoppingList WHERE id = :id LIMIT 1")
    abstract fun getShoppingListWithItemsById(id: Long): Flowable<ShoppingListWithItems>


    @Query("SELECT * FROM shoppingList WHERE id = :id LIMIT 1")
    abstract fun getShoppingListById(id: Long): Flowable<ShoppingList>

    @Query("UPDATE shoppingList SET archived = 1 WHERE id = :shoppingListId")
    abstract fun archiveShoppingList(shoppingListId: Long)

    @Query("UPDATE shoppingList SET archived = 1 WHERE id IN (:shoppingListIds)")
    abstract fun archiveShoppingLists(vararg shoppingListIds: Long)

    @Query("UPDATE shoppingList SET lastModificationDate = :modificationDate WHERE id = :shoppingListId")
    abstract fun updateShoppingListModification(shoppingListId: Long, modificationDate: Date = Date())


    @Query("UPDATE shoppingItem SET isCompleted = :completed WHERE id = :shoppingItemId")
    abstract fun setShoppingItemAsCompleted(shoppingItemId: Long, completed: Boolean)

    @Query("SELECT * FROM shoppingItem WHERE shoppingListId = :shoppingListId ORDER BY isCompleted ASC, itemName ASC")
    abstract fun getShoppingItemsByParentId(shoppingListId: Long): Flowable<List<ShoppingItem>>


    @Transaction
    open fun insertOrUpdateRefreshTime(shoppingItem: ShoppingItem) {
        insertOrUpdate(shoppingItem)
        updateShoppingListModification(shoppingItem.shoppingListId)
    }

    @Transaction
    open fun deleteRefreshTime(shoppingItem: ShoppingItem) {
        delete(shoppingItem)
        updateShoppingListModification(shoppingItem.shoppingListId)
    }

    @Transaction
    open fun setShoppingItemAsCompletedUpdateTime(shoppingItem: ShoppingItem, completed: Boolean) {
        setShoppingItemAsCompleted(shoppingItem.id!!, completed)
        updateShoppingListModification(shoppingItem.shoppingListId)
    }

    @Transaction
    open fun deleteShoppingListWithItems(shoppingList: ShoppingList) {
        deleteShoppingListItems(shoppingList.id!!)
        delete(shoppingList)
    }
}