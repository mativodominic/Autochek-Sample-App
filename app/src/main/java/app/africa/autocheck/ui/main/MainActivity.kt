package app.africa.autocheck.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import app.africa.autocheck.core.framework.ui.BaseActivity

class MainActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(fragment = MainFragment.getInstance(), needToAddToBackStack = false)
    }

    override fun onBackPressed() {
        mainFragmentHasBackStock()
    }

}