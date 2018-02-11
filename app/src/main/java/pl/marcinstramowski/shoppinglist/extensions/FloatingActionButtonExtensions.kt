package pl.marcinstramowski.shoppinglist.extensions

import android.support.design.widget.FloatingActionButton
import android.view.View

fun FloatingActionButton.setGone() {
    if (!isShown) visibility = View.GONE else hide()
}