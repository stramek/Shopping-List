package pl.marcinstramowski.shoppinglist.database.sources

import io.reactivex.Flowable
import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem
import pl.marcinstramowski.shoppinglist.database.model.ShoppingList
import pl.marcinstramowski.shoppinglist.database.model.ShoppingListWithItems

interface ShoppingListDataSource {

    fun observeArchivedListsWithItems(): Flowable<List<ShoppingListWithItems>>

    fun observeCurrentListsWithItems(): Flowable<List<ShoppingListWithItems>>

    fun observeShoppingListById(listId: Long): Flowable<ShoppingList>

    fun observeShoppingItemsByListId(listId: Long): Flowable<List<ShoppingItem>>


    fun deleteShoppingList(shoppingList: ShoppingList)

    fun archiveShoppingList(shoppingList: ShoppingList)

    fun updateShoppingListName(shoppingList: ShoppingList, newName: String)


    fun insertOrUpdateShoppingItem(shoppingItem: ShoppingItem)

    fun insertOrUpdateShoppingList(shoppingList: ShoppingList)

    fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun setShoppingItemCompleted(shoppingItem: ShoppingItem, completed: Boolean)

    fun updateShoppingItemName(shoppingItem: ShoppingItem, newName: String)

}