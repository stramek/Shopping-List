package pl.marcinstramowski.shoppinglist.screens.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

/**
 * Base application fragment
 */
abstract class BaseFragment<out T : BaseContract.Presenter> : DaggerFragment(),
    BaseContract.View<T> {

    /**
     * Defines layout resource of inflated view
     */
    abstract val contentViewId: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(contentViewId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreated(savedInstanceState)
    }

    abstract fun onCreated(savedInstanceState: Bundle?)

    override fun onStart() {
        super.onStart()
        presenter.onAttach()
    }

    override fun onStop() {
        presenter.onDetach()
        super.onStop()
    }
}