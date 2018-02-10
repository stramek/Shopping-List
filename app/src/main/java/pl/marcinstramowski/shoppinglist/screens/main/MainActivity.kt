package pl.marcinstramowski.shoppinglist.screens.main

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity
import pl.marcinstramowski.shoppinglist.R
import pl.marcinstramowski.shoppinglist.screens.base.BaseActivity
import pl.marcinstramowski.shoppinglist.screens.main.ListDetails.ListDetailsActivity
import javax.inject.Inject

class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

    companion object { const val SELECTED_PAGE = "SELECTED_PAGE" }

    @Inject override lateinit var presenter: MainContract.Presenter

    override val contentViewId = R.layout.activity_main

    enum class ShoppingListPages(@StringRes internal val titleRes: Int) {
        CURRENT_LISTS(R.string.current_lists_title),
        ARCHIVED_LISTS(R.string.archived_lists_title)
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        configureViewPager()
        fab.setOnClickListener {
            startActivity<ListDetailsActivity>()
        }
        //longSnackbar(coordinator, "lol")

    }

    private fun configureViewPager() {
        viewPager.adapter = ShoppingListPagerAdapter(supportFragmentManager, applicationContext)
        viewPager.currentItem = intent.getIntExtra(SELECTED_PAGE, ShoppingListPages.CURRENT_LISTS.ordinal)
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) = when (ShoppingListPages.values()[position]) {
                ShoppingListPages.CURRENT_LISTS -> fab.show()
                ShoppingListPages.ARCHIVED_LISTS -> fab.hide()
            }
        })
    }

    fun showLongSnackbar(@StringRes textRes: Int) = longSnackbar(coordinator, textRes)

    fun showSnackbar(@StringRes textRes: Int) = snackbar(coordinator, textRes)
}
