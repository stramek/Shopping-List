package pl.marcinstramowski.shoppinglist.database.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ShoppingItem(
    var shoppingListId: Long,
    var itemName: String
) {
    @PrimaryKey(autoGenerate = true) var id: Long? = null
    var isCompleted = false
}