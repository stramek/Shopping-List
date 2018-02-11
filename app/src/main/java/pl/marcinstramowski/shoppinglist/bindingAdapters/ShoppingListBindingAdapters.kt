package pl.marcinstramowski.shoppinglist.bindingAdapters

import android.databinding.BindingAdapter
import android.widget.TextView
import pl.marcinstramowski.shoppinglist.database.model.ShoppingItem

@BindingAdapter("setItemsText")
fun setItemsText(textView: TextView, items: List<ShoppingItem>?) {
    items?.let {
        textView.text = items.filter { !it.isCompleted }.map { it.itemName }.joinToString()
    }
}