package pl.marcinstramowski.shoppinglist.database.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import pl.marcinstramowski.shoppinglist.database.converters.DateConverter
import java.util.*

@Entity
data class ShoppingList(
    val listName: String
) {
    @PrimaryKey(autoGenerate = true) var id: Long? = null
    var archived: Boolean = false

    @TypeConverters(DateConverter::class) var lastModificationDate: Date? = Date()
}
