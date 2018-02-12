package pl.marcinstramowski.shoppinglist.screens.main

import pl.marcinstramowski.shoppinglist.screens.base.BaseContract

/**
 * Contract interfaces between [MainActivity] and [MainPresenter]
 */
interface MainContract {

    interface View : BaseContract.View<Presenter> {

        fun showAddNewListDialog()

    }

    interface Presenter : BaseContract.Presenter {

        fun onFabButtonClick()

        fun createNewList(listName: String)

    }
}