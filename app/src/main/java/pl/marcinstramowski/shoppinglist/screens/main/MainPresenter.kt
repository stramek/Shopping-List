package pl.marcinstramowski.shoppinglist.screens.main

import timber.log.Timber
import javax.inject.Inject

/**
 * MainActivity login
 */
class MainPresenter @Inject constructor(val view: MainContract.View) : MainContract.Presenter {

    override fun onAttach() {
        Timber.i("onAttach")
    }

    override fun onDetach() {
        Timber.i("onDetach")
    }
}