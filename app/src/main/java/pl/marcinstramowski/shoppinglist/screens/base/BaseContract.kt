package pl.marcinstramowski.shoppinglist.screens.base

/**
 * Contract interfaces between [BaseActivity] or [BaseActivity]
 */
interface BaseContract {

    interface View<out T : Presenter> {

        val presenter: T

    }

    interface Presenter {

        fun onAttach()

        fun onDetach()

    }
}