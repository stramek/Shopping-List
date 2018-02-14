package pl.marcinstramowski.shoppinglist.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import pl.marcinstramowski.shoppinglist.database.AppDatabase
import pl.marcinstramowski.shoppinglist.database.dao.ShoppingListDao
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "shopping-list.db").build()
    }

    @Provides
    @Singleton
    fun providesShoppingListDao(appDatabase: AppDatabase): ShoppingListDao = appDatabase.shoppingListDao()

}