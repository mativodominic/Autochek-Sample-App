package app.africa.autocheck.core.framework.ui

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import app.africa.autocheck.ui.main.MainFragment
import timber.log.Timber

/**
 * @author Dominic Mativo
 */
abstract class BaseMvpActivity : AppCompatActivity() {

    /**
     * Add Fragment to activity.
     * @param fragment [Fragment] The fragment to add.
     * @param containerId [Int] The Container View ID.
     * @param needToAddToBackStack Boolean If we should add the fragment to the back-stack.
     */
    protected open fun replaceFragment(containerId: Int = android.R.id.content, fragment: Fragment, needToAddToBackStack: Boolean = true) {
        val name = fragment.javaClass.simpleName
        with(supportFragmentManager.beginTransaction()) {
            replace(containerId, fragment, name)
            if (needToAddToBackStack) {
                addToBackStack(name)
            }

            commit()
        }
    }

    inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
        crossinline bindingInflater: (LayoutInflater) -> T) =
        lazy(LazyThreadSafetyMode.NONE) {
            bindingInflater.invoke(layoutInflater)
        }

    open fun onMoveBack() {
        supportFragmentManager.popBackStack()
    }

    open fun hasBackStack(): Boolean {
        val transaction = supportFragmentManager.backStackEntryCount
        return transaction > 0
    }
    open fun mainFragmentPopOutAll() {
        val mainFragment = supportFragmentManager.findFragmentById(android.R.id.content)
        if (mainFragment is MainFragment) {
            val mainHasStack = mainFragment.hasBackStack()
            val mainStackSize = mainFragment.childFragmentManager.backStackEntryCount

            if (mainHasStack) {
                for (s in 0 until mainStackSize) {
                    mainFragment.childFragmentManager.popBackStack()
                }
            }
        }
    }

    open fun mainFragmentHasBackStock() {
        val mainFragment = supportFragmentManager.findFragmentById(android.R.id.content)
        if (mainFragment is MainFragment) {
            val mainHasStack = mainFragment.hasBackStack()

            if (mainHasStack) {
                mainFragment.childFragmentManager.popBackStack()
            } else {
                finish()
            }
        } else finish()
    }
}