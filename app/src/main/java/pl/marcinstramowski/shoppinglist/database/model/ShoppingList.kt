package pl.marcinstramowski.shoppinglist.database.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import pl.marcinstramowski.shoppinglist.database.converters.DateConverter
import java.util.*

@Entity
class ShoppingList(
    val listName: String
) {
    @PrimaryKey(autoGenerate = true) var id: Long? = null
    var archived: Boolean = false

    @TypeConverters(DateConverter::class) var lastModificationDate: Date? = Date()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShoppingList

        if (listName != other.listName) return false
        if (id != other.id) return false
        if (archived != other.archived) return false
        if (lastModificationDate != other.lastModificationDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = listName.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        result = 31 * result + archived.hashCode()
        result = 31 * result + (lastModificationDate?.hashCode() ?: 0)
        return result
    }
}
