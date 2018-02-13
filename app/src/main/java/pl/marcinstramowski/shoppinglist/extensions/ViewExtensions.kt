package pl.marcinstramowski.shoppinglist.extensions

import android.view.View

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}
