package pl.marcinstramowski.shoppinglist.database

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import pl.marcinstramowski.shoppinglist.database.dao.ShoppingListDao
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "shopping-list.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesShoppingListDao(appDatabase: AppDatabase): ShoppingListDao = appDatabase.shoppingListDao()

}