package pl.marcinstramowski.shoppinglist.screens.main

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import pl.marcinstramowski.shoppinglist.screens.main.currentLists.CurrentListsFragment

class ShoppingListPagerAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = when (MainActivity.ShoppingListPages.values()[position]) {
        MainActivity.ShoppingListPages.CURRENT_LISTS -> CurrentListsFragment()
        MainActivity.ShoppingListPages.ARCHIVED_LISTS -> CurrentListsFragment()
    }

    override fun getPageTitle(position: Int): String {
        return context.getString(MainActivity.ShoppingListPages.values()[position].titleRes)
    }

    override fun getCount() = MainActivity.ShoppingListPages.values().size


}