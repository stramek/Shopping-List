package pl.marcinstramowski.shoppinglist.screens.base

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity

/**
 * Base application activity
 */
abstract class BaseActivity<out T : BaseContract.Presenter> : DaggerAppCompatActivity(), BaseContract.View<T> {

    /**
     * Defines layout resource of activity content view
     */
    abstract val contentViewId : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewId)
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

    /**
     * Sets (no back stack) passed [fragment] to view of assigned [containerId] without animation.
     */
    fun <T : BaseFragment<*>> setFragmentNoAnimation(fragment: T, containerId: Int) {
        supportFragmentManager.beginTransaction().apply { replace(containerId, fragment) }.commit()
    }


}
