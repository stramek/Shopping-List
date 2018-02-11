package pl.marcinstramowski.shoppinglist.database.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import pl.marcinstramowski.shoppinglist.utils.UniqueId

class ShoppingListWithItems : UniqueId {

    @Embedded var shoppingList: ShoppingList? = null

    @Relation(parentColumn = "id", entityColumn = "shoppingListId")
    var shoppingItems: List<ShoppingItem> = listOf()

    fun getNumberOfCompletedItems() = shoppingItems.count { it.isCompleted }

    fun getNumberOfNotCompletedItems() = shoppingItems.count { !it.isCompleted }

    fun getNumberOfItems() = shoppingItems.size

    override fun getUniqueId() = shoppingList!!.id!!
    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShoppingListWithItems

        if (shoppingList != other.shoppingList) return false
        if (shoppingItems != other.shoppingItems) return false

        return true
    }

    override fun hashCode(): Int {
        var result = shoppingList?.hashCode() ?: 0
        result = 31 * result + shoppingItems.hashCode()
        return result
    }
}