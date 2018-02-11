package pl.marcinstramowski.shoppinglist.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems

@Dao
abstract class ShoppingListDao {

    @Transaction
    @Query("SELECT * FROM shoppingList WHERE archived = 0 ORDER BY lastModificationDate DESC")
    abstract fun getCurrentListsWithItems(): Flowable<List<ShoppingListWithItems>>

    @Transaction
    @Query("SELECT * FROM shoppingList WHERE archived = 1 ORDER BY lastModificationDate DESC")
    abstract fun getArchivedListsWithItems(): Flowable<List<ShoppingListWithItems>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrUpdate(shoppingList: ShoppingList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrUpdate(vararg shoppingItems: ShoppingItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrUpdate(shoppingItems: List<ShoppingItem>)

    @Query("SELECT * FROM shoppingList WHERE id = :id LIMIT 1")
    abstract fun getShoppingListById(id: Long): Flowable<ShoppingList>

    @Delete
    abstract fun delete(vararg shoppingLists: ShoppingList)

    @Delete
    abstract fun delete(vararg shoppingItems: ShoppingItem)

    @Query("DELETE FROM shoppingList")
    abstract fun deleteAll()

    @Query("DELETE FROM shoppingitem WHERE shoppingListId = :shoppingListId")
    abstract fun deleteShoppingListItems(shoppingListId: Long)

    @Query("UPDATE shoppingList SET archived = 1 WHERE id = :shoppingListId")
    abstract fun archiveShoppingList(shoppingListId: Long)

    @Query("UPDATE shoppingList SET archived = 1 WHERE id IN (:shoppingListIds)")
    abstract fun archiveShoppingLists(vararg shoppingListIds: Long)

    @Transaction
    open fun deleteShoppingListWithItems(shoppingList: ShoppingList) {
        deleteShoppingListItems(shoppingList.id!!)
        delete(shoppingList)
    }

    @Transaction
    open fun insertOrUpdate(shoppingListWithItems: ShoppingListWithItems) {
        insertOrUpdate(shoppingListWithItems.shoppingList!!)
        insertOrUpdate(shoppingListWithItems.shoppingItems)
    }
}