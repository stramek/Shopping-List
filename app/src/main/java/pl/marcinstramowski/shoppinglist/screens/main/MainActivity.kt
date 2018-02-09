package pl.marcinstramowski.shoppinglist.screens.main

import android.os.Bundle
import pl.marcinstramowski.shoppinglist.R
import pl.marcinstramowski.shoppinglist.screens.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

    @Inject override lateinit var presenter: MainContract.Presenter

    override val contentViewId = R.layout.activity_main

    override fun onCreated(savedInstanceState: Bundle?) {

    }
}
