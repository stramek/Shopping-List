package pl.marcinstramowski.shoppinglist.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM shoppingList")
    fun getAll(): Flowable<List<ShoppingList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shoppingList: ShoppingList)

    @Delete
    fun delete(shoppingList: ShoppingList)

    @Query("DELETE FROM shoppingList")
    fun deleteAll()

}