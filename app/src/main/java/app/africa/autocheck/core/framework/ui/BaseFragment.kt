package app.africa.autocheck.core.framework.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar

/**
 * @author Dominic Mativo
 */
abstract class BaseFragment(@LayoutRes rootLayoutId: Int) : Fragment(rootLayoutId) {

    private var vProgress: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBarView()?.let { vProgress = it }
    }

    /**
     * Toggle the visibility of any [View] associated with progress.
     * @param show Visibility of the [View].
     */
    protected open fun toggleProgressContent(show: Boolean) {}

    protected abstract fun progressBarView(): View?

    open fun onShowProgress() {
        vProgress?.visibility = View.VISIBLE
        toggleProgressContent(false)
    }

    open fun onHideProgress() {
        vProgress?.visibility = View.GONE
        toggleProgressContent(true)
    }

    /**
     * Check if the current fragment has fragments on its stack
     */
    open fun hasBackStack(): Boolean {
        val transaction = childFragmentManager.backStackEntryCount
        return transaction > 0
    }

    open fun stackSize() : Int  = parentFragment!!.childFragmentManager.backStackEntryCount

    /**
     * Check if the parent fragment has fragments on its stack
     */
    open fun parentHasBackStack(): Boolean {
        if (parentFragment == null) return hasBackStack()
        val transaction =
            parentFragment!!.childFragmentManager.backStackEntryCount
        return transaction > 0
    }

    /**
     * Navigate back on the fragment manager
     */
    open fun onMoveBack() {
        childFragmentManager.popBackStack()
    }

    open fun snackbar(message: String?) {
        view?.let { v -> Snackbar.make(v, message!!, Snackbar.LENGTH_LONG).show() }
    }

    open fun snackbar(message: Int) {
        view?.let { v -> Snackbar.make(v, message, Snackbar.LENGTH_LONG).show() }
    }

    open fun replaceFragment(fragment: Fragment, containerId: Int, addToBackStack: Boolean = false) {
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        if (addToBackStack) transaction.addToBackStack(fragment.javaClass.name)
        transaction.commit()
    }

}