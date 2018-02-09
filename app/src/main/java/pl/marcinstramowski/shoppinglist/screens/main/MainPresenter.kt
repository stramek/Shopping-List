package pl.marcinstramowski.shoppinglist.screens.main

import timber.log.Timber
import javax.inject.Inject

/**
 * MainActivity login
 */
class MainPresenter @Inject constructor(val view: MainContract.View) : MainContract.Presenter {

    override fun onCreate() {
        Timber.i("onCreate")
    }

    override fun onDestroy() {
        Timber.i("onDestroy")
    }

    override fun onStart() {
        Timber.i("onStart")
    }

    override fun onStop() {
        Timber.i("onStop")
    }
}