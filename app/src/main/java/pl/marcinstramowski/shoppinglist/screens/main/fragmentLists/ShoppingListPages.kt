package pl.marcinstramowski.shoppinglist.screens.main.fragmentLists

import android.support.annotation.StringRes
import pl.marcinstramowski.shoppinglist.R

enum class ShoppingListPages(@StringRes internal val titleRes: Int) {
    CURRENT_LISTS(R.string.current_lists_title),
    ARCHIVED_LISTS(R.string.archived_lists_title)
}