package app.africa.autocheck.ui.car_details

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.palette.graphics.Palette
import app.africa.autocheck.R
import app.africa.autocheck.core.data.cars.Car
import app.africa.autocheck.core.data.cars.CarMedia
import app.africa.autocheck.core.framework.data.AutoState
import app.africa.autocheck.core.framework.ui.BaseMvpFragment
import app.africa.autocheck.core.framework.ui.viewBinding
import app.africa.autocheck.core.framework.utils.format
import app.africa.autocheck.core.framework.utils.formatAmount
import app.africa.autocheck.databinding.CarDetailsFragmentBinding
import app.africa.autocheck.ui.main.MainFragment
import coil.load

class CarDetailsFragment : BaseMvpFragment<CarDetailsViewModel>(R.layout.car_details_fragment) {

    companion object {
        fun getInstance(car: Car) : CarDetailsFragment {
            val fragment = CarDetailsFragment()
            fragment.arguments = bundleOf(Pair (EXTRA_MODEL, car))
            return fragment
        }
    }

    private lateinit var carModel: Car
    private lateinit var mediaAdapter : CarMediaListAdapter
    private val binding by viewBinding(CarDetailsFragmentBinding::bind)
    override val viewModelClass: Class<CarDetailsViewModel>
        get() = CarDetailsViewModel::class.java

    override fun observeLiveData(viewModel: CarDetailsViewModel) {
        setupAdapter()
        viewModel.car = carModel
        viewModel.loadMedia()
        viewModel.loadCarDetails()

        viewModel.displayMediaState.observe(this) {
            if (it is AutoState.Success) {
                viewModel.selectedMedia?.let {
                    loadMedia(it)
                }
            }
        }

        viewModel.loadDetailsState.observe(this) {}

        viewModel.loadMediaState.observe(this) {
            when (it) {
                is AutoState.Loading -> onShowProgress()
                is AutoState.Success -> {
                    onHideProgress()
                    mediaAdapter.notifyDataSetChanged()
                }
                is AutoState.Error -> {
                    onHideProgress()
                    snackbar(R.string.error_loading_car_media)
                }
                else -> {}
            }
        }

    }

    private fun loadMedia(media: CarMedia) {
        if (media.isVideo()) {

        }
        else {
            binding.carImage.load(media.url) {
                crossfade(true)

                allowHardware(false)
                listener(
                    onSuccess = { _, result ->
                        Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                            val context = binding.root.context
                            val bgColor = palette?.getDarkVibrantColor(
                                ContextCompat.getColor(context, R
                                    .color.featured_car_bg))
                            if (bgColor != null) {
                                binding.carImage.setBackgroundColor(bgColor)
                            }
                        }
                    }
                )
            }

        }
    }

    private fun setupAdapter() {
        mediaAdapter = CarMediaListAdapter(viewModel, layoutInflater)
        binding.carMediaList.apply {
            adapter = mediaAdapter
            setHasFixedSize(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { b ->
            if (b.containsKey(EXTRA_MODEL)) carModel = b.getSerializable(EXTRA_MODEL) as Car
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            val parent = parentFragment as MainFragment
            parent.onMoveBack()
        }

        showSummary()
    }

    private fun showSummary() {
        binding.carTitle.text = carModel.title

        if (carModel.city.isNullOrEmpty()) {
            binding.location.visibility = View.GONE
        }
        else binding.location.text = carModel.city

        binding.carImage.load(carModel.imageUrl) {
            crossfade(true)

            allowHardware(false)
            listener(
                onSuccess = { _, result ->
                    Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                        val context = binding.root.context
                        val bgColor = palette?.getDarkVibrantColor(
                            ContextCompat.getColor(context, R
                            .color.featured_car_bg))
                        if (bgColor != null) {
                            binding.carImage.setBackgroundColor(bgColor)
                        }
                    }
                }
            )
        }

        setIsFavourite()

        binding.favouriteIcon.setOnClickListener {
            viewModel.car.isFavourite = !viewModel.car.isFavourite
            setIsFavourite()
        }

        binding.yearOfManufacture.text = carModel.year.toString()

        val mileage = "${carModel.mileage.format()} ${carModel.mileageUnit}"
        binding.mileage.text = mileage

        val amount = "$${carModel.marketplacePrice.format()}"
        binding.costOfCar.text = amount

        val rating = "(${ carModel.gradeScore.formatAmount() })"
        binding.ratingLevel.text = rating

    }

    private fun setIsFavourite() {
        binding.favouriteIcon.setImageResource(
            if (carModel.isFavourite) R.drawable.favourite_active
            else R.drawable.favourite_normal
        )
    }

    override fun progressBarView(): View? = binding.progressBar

}