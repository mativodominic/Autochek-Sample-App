package app.africa.autocheck.ui.main

import android.os.Bundle
import android.view.View
import app.africa.autocheck.R
import app.africa.autocheck.core.data.cars.Car
import app.africa.autocheck.core.framework.ui.BaseFragment
import app.africa.autocheck.core.framework.ui.viewBinding
import app.africa.autocheck.databinding.ContentMainBinding
import app.africa.autocheck.ui.car_details.CarDetailsFragment
import app.africa.autocheck.ui.home.HomeFragment

class MainFragment : BaseFragment(R.layout.content_main) {

    companion object {
        fun getInstance() = MainFragment()
    }

    private val binding by viewBinding(ContentMainBinding::bind)
    override fun progressBarView(): View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        binding.bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.actionHome -> {
                    openHome()
                }
                R.id.actionCart -> {
                    openCart()
                }
                R.id.actionFav -> {
                    openFavourite()
                }
            }
            true
        }
    }

    private fun openHome() {
        replaceFragment(HomeFragment.getInstance(), R.id.contentMainContainer, false)
    }

    private fun openCart() {

    }

    private fun openFavourite() {

    }

    override fun onStart() {
        super.onStart()

        when (childFragmentManager.findFragmentById(R.id.contentMainContainer)) {
            is HomeFragment -> {}
            else -> openHome()
        }
    }

    fun isPrimaryMenus(): Boolean {
        return when (childFragmentManager.findFragmentById(R.id.contentMainContainer)) {
            is HomeFragment -> true
            else -> false
        }
    }

    fun onBottomNavHome() {
        binding.bottomNavigation.selectedItemId = R.id.actionHome
    }

    fun openCarDetails(car: Car) {
        replaceFragment(CarDetailsFragment.getInstance(car), R.id.contentMainContainer, true)
    }

}