package pl.marcinstramowski.shoppinglist.database.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import pl.marcinstramowski.shoppinglist.utils.UniqueId
import java.util.*

@Entity
data class ShoppingList(
    val listName: String//,
    //@Embedded(prefix = "items") val items: List<ShoppingItem>
): UniqueId {
    @PrimaryKey(autoGenerate = true) var id: Long? = null

    override fun getUniqueId(): Long = id!!
}
