package pl.marcinstramowski.shoppinglist.screens.base

import android.os.Bundle
import android.support.annotation.CallSuper
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Base application activity
 */
abstract class BaseActivity<out T : BaseContract.Presenter> : DaggerAppCompatActivity(), BaseContract.View<T> {

    /**
     * Defines layout resource of activity content view
     */
    abstract val contentViewId : Int

    @CallSuper override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewId)
        setSupportActionBar(toolbar)
        onCreated(savedInstanceState)
    }

    abstract fun onCreated(savedInstanceState: Bundle?)


    @CallSuper override fun onStart() {
        super.onStart()
        presenter.onAttach()
    }

    @CallSuper override fun onStop() {
        presenter.onDetach()
        super.onStop()
    }
}
