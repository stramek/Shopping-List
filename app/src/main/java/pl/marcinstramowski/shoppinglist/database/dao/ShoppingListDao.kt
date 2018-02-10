package pl.marcinstramowski.shoppinglist.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM shoppingList")
    fun getAll(): Flowable<List<ShoppingList>>

    @Transaction
    @Query("SELECT * FROM shoppingList ORDER BY lastModificationDate DESC")
    fun getAllWithItems(): Flowable<List<ShoppingListWithItems>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shoppingList: ShoppingList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shoppingList WHERE id LIKE :id LIMIT 1")
    fun getShoppingListById(id: Long): Flowable<ShoppingList>

    @Delete
    fun delete(shoppingList: ShoppingList)

    @Query("DELETE FROM shoppingList")
    fun deleteAll()

    @Query("DELETE FROM shoppingitem WHERE shoppingListId LIKE :shoppingListId")
    fun deleteShoppingListItems(shoppingListId: Long)

}