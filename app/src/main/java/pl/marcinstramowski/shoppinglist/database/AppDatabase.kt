package pl.marcinstramowski.shoppinglist.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import pl.marcinstramowski.shoppinglist.database.dao.ShoppingListDao
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList

@Database(entities = [ShoppingList::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListDao

}