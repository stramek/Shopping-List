package pl.marcinstramowski.shoppinglist.screens.main

import pl.marcinstramowski.shoppinglist.screens.base.BaseContract

/**
 * Contract interfaces between [MainActivity] and [MainPresenter]
 */
interface MainContract {

    interface View : BaseContract.View<Presenter> {

    }

    interface Presenter : BaseContract.Presenter {

    }
}