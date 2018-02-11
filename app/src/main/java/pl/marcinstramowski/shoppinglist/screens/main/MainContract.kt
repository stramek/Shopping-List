package pl.marcinstramowski.shoppinglist.screens.main

import pl.marcinstramowski.shoppinglist.screens.base.BaseContract

/**
 * Contract interfaces between [MainActivity] and [MainPresenter]
 */
interface MainContract {

    interface View : BaseContract.View<Presenter> {
        fun showAddNewListActivity()
    }

    interface Presenter : BaseContract.Presenter {
        fun onFabButtonClick()
    }
}