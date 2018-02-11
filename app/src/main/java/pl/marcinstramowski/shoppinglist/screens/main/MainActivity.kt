package pl.marcinstramowski.shoppinglist.screens.main

import android.os.Bundle
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import pl.marcinstramowski.shoppinglist.R
import pl.marcinstramowski.shoppinglist.extensions.setGone
import pl.marcinstramowski.shoppinglist.screens.base.BaseActivity
import pl.marcinstramowski.shoppinglist.screens.ListDetails.ListDetailsActivity
import pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.ShoppingListPagerAdapter
import pl.marcinstramowski.shoppinglist.screens.main.fragmentLists.ShoppingListPages
import javax.inject.Inject

class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

    companion object { const val SELECTED_PAGE = "SELECTED_PAGE" }

    @Inject override lateinit var presenter: MainContract.Presenter

    override val contentViewId = R.layout.activity_main

    override fun onCreated(savedInstanceState: Bundle?) {
        configureViewPager()
        fab.setOnClickListener { startActivity<ListDetailsActivity>() }
    }

    private fun configureViewPager() {
        viewPager.apply {
            adapter = ShoppingListPagerAdapter(
                supportFragmentManager,
                applicationContext
            )
            currentItem = intent.getIntExtra(SELECTED_PAGE, ShoppingListPages.CURRENT_LISTS.ordinal)
            addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) = when (ShoppingListPages.values()[position]) {
                    ShoppingListPages.CURRENT_LISTS -> { fab.show() }
                    ShoppingListPages.ARCHIVED_LISTS -> { fab.setGone() }
                }
            })
        }
        tabLayout.setupWithViewPager(viewPager)
    }
}
