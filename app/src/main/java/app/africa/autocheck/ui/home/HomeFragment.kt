package app.africa.autocheck.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import app.africa.autocheck.R
import app.africa.autocheck.core.framework.data.AutoReloadState
import app.africa.autocheck.core.framework.data.AutoState
import app.africa.autocheck.core.framework.ui.BaseMvpFragment
import app.africa.autocheck.core.framework.ui.viewBinding
import app.africa.autocheck.databinding.HomeFragmentBinding
import app.africa.autocheck.ui.main.MainFragment

class HomeFragment : BaseMvpFragment<HomeViewModel>(R.layout.home_fragment) {

    companion object {
        fun getInstance() = HomeFragment()
    }

    private var cartBadgeView: TextView? = null

    private lateinit var makesAdapter: PopularMakeListAdapter
    private lateinit var carsAdapter: CarListAdapter
    private val binding by viewBinding(HomeFragmentBinding::bind)

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun observeLiveData(viewModel: HomeViewModel) {
        viewModel.loadPopularMakes()
        viewModel.loadCars()

        setupPopularMakesAdapter()
        setupCarsAdapter()

        viewModel.loadMakesState.observe(this) {
            when (it) {
                is AutoState.Loading -> onShowProgress()
                is AutoState.Success -> {
                    onHideProgress()
                    makesAdapter.notifyDataSetChanged()
                }
                is AutoState.Error -> {
                    onHideProgress()
                    snackbar(R.string.error_loading_popular_makes)
                }
                else -> {}
            }
        }

        viewModel.loadCarsState.observe(this) {
            when (it) {
                is AutoState.Loading -> onShowProgress()
                is AutoState.Success -> {
                    onHideProgress()
                    carsAdapter.notifyDataSetChanged()
                }
                is AutoState.Error -> {
                    onHideProgress()
                    snackbar(R.string.error_loading_popular_makes)
                }
                else -> {}
            }
        }

        viewModel.loadMoreCarsState.observe(this) {
            when (it) {
                is AutoState.Loading -> toggleProgress(true)
                is AutoState.Success -> {
                    toggleProgress(false)
                    carsAdapter.notifyDataSetChanged()
                }
                is AutoState.Error -> {
                    toggleProgress(false)
                    snackbar(R.string.error_loading_popular_makes)
                }
                else -> {}
            }
        }

        viewModel.reloadState.observe(this) {
            if (it is AutoReloadState.Success) {
                carsAdapter.notifyItemChanged(it.position)
            }
        }

        viewModel.viewDetailsState.observe(this) {
            val parent = parentFragment as MainFragment
            parent.openCarDetails(it)
        }
    }

    private fun setupPopularMakesAdapter() {
        makesAdapter = PopularMakeListAdapter(viewModel, layoutInflater)
        binding.popularMakesList.apply {
            adapter = makesAdapter
            setHasFixedSize(true)
        }
    }

    private fun  setupCarsAdapter() {
        carsAdapter = CarListAdapter(viewModel, layoutInflater)
        binding.popularCarsList.apply {
            adapter = carsAdapter
            setHasFixedSize(true)
        }
    }

    override fun progressBarView(): View = binding.loadProgressBar

    private fun toggleProgress(show: Boolean) {
        binding.progressView.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.cart_menu, menu)
        val actionView = menu.findItem(R.id.actionCart).actionView
        cartBadgeView = actionView.findViewById(R.id.cartBadge)

        cartBadgeView?.visibility = View.GONE

        actionView.setOnClickListener {}
    }

}