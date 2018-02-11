package pl.marcinstramowski.shoppinglist.utils

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View
import timber.log.Timber

@Suppress("unused")
class ScrollAwareFabBehaviour(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<FloatingActionButton>() {


    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
                                     child: FloatingActionButton, directTargetChild: View,
                                     target: View, nestedScrollAxes: Int, type: Int): Boolean {
        return true
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
                                target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
                                dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, type)

        if ((dyConsumed > 0 || dyUnconsumed > 0) && child.isShown) {
            hideFab(child)
        } else if ((dyConsumed < 0 || dyUnconsumed < 0) && !child.isShown) {
            child.show()
        }
    }

    private fun hideFab(fab: FloatingActionButton) {
        fab.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
            override fun onHidden(fab: FloatingActionButton?) {
                super.onHidden(fab)
                fab?.visibility = View.INVISIBLE
            }
        })
    }
}