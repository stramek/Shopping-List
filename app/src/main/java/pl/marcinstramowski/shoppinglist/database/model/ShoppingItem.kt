package pl.marcinstramowski.shoppinglist.database.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import pl.marcinstramowski.shoppinglist.utils.UniqueId

@Entity
class ShoppingItem(
    var shoppingListId: Long,
    var itemName: String
) : UniqueId {

    @PrimaryKey(autoGenerate = true) var id: Long? = null

    var isCompleted = false

    override fun getUniqueId() = id!!

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShoppingItem

        if (shoppingListId != other.shoppingListId) return false
        if (itemName != other.itemName) return false
        if (id != other.id) return false
        if (isCompleted != other.isCompleted) return false

        return true
    }

    override fun hashCode(): Int {
        var result = shoppingListId.hashCode()
        result = 31 * result + itemName.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        result = 31 * result + isCompleted.hashCode()
        return result
    }
}