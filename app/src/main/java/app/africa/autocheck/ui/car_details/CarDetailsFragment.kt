package app.africa.autocheck.ui.car_details

import android.app.ActivityOptions
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.palette.graphics.Palette
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
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
import app.africa.autocheck.ui.photo_detail.PhotoDetailActivity
import coil.load

class CarDetailsFragment : BaseMvpFragment<CarDetailsViewModel>(R.layout.car_details_fragment) {

    companion object {
        fun getInstance(car: Car): CarDetailsFragment {
            val fragment = CarDetailsFragment()
            fragment.arguments = bundleOf(Pair(EXTRA_MODEL, car))
            return fragment
        }
    }

    private lateinit var mediaController: MediaController
    private lateinit var carModel: Car
    private lateinit var mediaAdapter: CarMediaListAdapter
    private lateinit var descriptionAdapter: CarDescListAdapter
    private val binding by viewBinding(CarDetailsFragmentBinding::bind)
    override val viewModelClass: Class<CarDetailsViewModel>
        get() = CarDetailsViewModel::class.java

    override fun observeLiveData(viewModel: CarDetailsViewModel) {
        setupAdapter()
        //setupVideo()
        viewModel.car = carModel
        viewModel.loadMedia()
        viewModel.loadCarDetails()

        viewModel.displayMediaState.observe(this) { state ->
            if (state is AutoState.Success) {
                viewModel.selectedMedia?.let {
                    loadMedia(it)
                }
            }
        }

        viewModel.loadDetailsState.observe(this) {
            when (it) {
                is AutoState.Loading -> onShowProgress()
                is AutoState.Success -> {
                    onHideProgress()
                    binding.vehicleDescContainer.visibility = View.VISIBLE
                    showMoreDetails()
                }
                else -> {
                    onHideProgress()
                    binding.vehicleDescContainer.visibility = View.GONE
                }
            }
        }

        viewModel.loadMediaState.observe(this) {
            when (it) {
                is AutoState.Loading -> onShowProgress()
                is AutoState.Success -> {
                    onHideProgress()
                    mediaAdapter.notifyDataSetChanged()
                    val mFade = Fade(Fade.IN)
                    TransitionManager.beginDelayedTransition(binding.root, mFade)
                    binding.carMediaList.visibility = View.VISIBLE
                }
                is AutoState.Error -> {
                    onHideProgress()
                    snackbar(R.string.error_loading_car_media)
                }
                else -> {}
            }
        }
    }

    private fun showMoreDetails() {
        viewModel.description.clear()
        binding.sellingCondition.text = viewModel.carDetails?.sellingCondition ?: ""

        addDescription(getString(R.string.engine_type), viewModel.carDetails?.engineType ?: "")
        addDescription(getString(R.string.engine_capacity), "")
        addDescription(
            getString(R.string.engine_transmission),
            viewModel.carDetails?.transmission ?: ""
        )
        addDescription(getString(R.string.fuel_type), viewModel.carDetails?.fuelType ?: "")

        addDescription(
            getString(R.string.interior_color),
            viewModel.carDetails?.interiorColor ?: ""
        )
        addDescription(
            getString(R.string.exterior_color),
            viewModel.carDetails?.exteriorColor ?: ""
        )
        addDescription(getString(R.string.vin), viewModel.carDetails?.vin ?: "")
        addDescription(getString(R.string.wheel_type), viewModel.carDetails?.model?.wheelType ?: "")
        addDescription(getString(R.string.body_type), viewModel.carDetails?.bodyType?.name ?: "")
        addDescription(getString(R.string.vehicle_id), viewModel.carDetails?.id)

        descriptionAdapter.notifyDataSetChanged()

        val mFade = Fade(Fade.IN)
        TransitionManager.beginDelayedTransition(binding.root, mFade)
        binding.vehicleDescContainer.visibility = View.VISIBLE
    }

    private fun addDescription(title: String, value: String?) {
        viewModel.description.add(VehicleDescription(title, value))
    }

    private fun setupVideo() {
        try {
            mediaController = MediaController(requireContext())
            mediaController.setAnchorView(binding.carVideo)
            mediaController.setMediaPlayer(binding.carVideo)
            binding.carVideo.setMediaController(mediaController)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun loadMedia(media: CarMedia) {
        if (media.isVideo()) {
            binding.carVideo.visibility = View.VISIBLE
            binding.carVideo.setVideoPath(media.url)

            setupVideo()

            binding.carVideo.requestFocus()
            binding.carVideo.start()
        } else {
            binding.carVideo.visibility = View.GONE
            binding.carImage.load(media.url) {
                crossfade(true)

                allowHardware(false)
                listener(
                    onSuccess = { _, result ->
                        Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                            val context = binding.root.context
                            val bgColor = palette?.getDarkVibrantColor(
                                ContextCompat.getColor(
                                    context, R
                                        .color.featured_car_bg
                                )
                            )
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

        descriptionAdapter = CarDescListAdapter(viewModel, layoutInflater)
        binding.vehicleDescriptionList.apply {
            adapter = descriptionAdapter
            setHasFixedSize(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { b ->
            if (b.containsKey(EXTRA_MODEL)) carModel = b.getSerializable(EXTRA_MODEL) as Car
        }

        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.change_image_transform)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        binding.toolbar.setNavigationOnClickListener {
            val parent = parentFragment as MainFragment
            parent.onMoveBack()
        }

        showSummary()

        binding.carImage.setOnClickListener {
            val options = ActivityOptions.makeSceneTransitionAnimation(
                requireActivity(), binding
                    .carImage, binding.carImage.transitionName
            )
            startActivity(
                PhotoDetailActivity.createIntent(
                    requireContext(),
                    ArrayList(viewModel.mediaList.map { it.url }),
                    viewModel.selectedMedia?.position ?: 0
                ), options.toBundle()
            )

            requireActivity().overridePendingTransition(R.anim.fade_in_fast, 0)
        }
    }

    private fun showSummary() {
        binding.carTitles.text = carModel.title

        if (carModel.city.isNullOrEmpty()) {
            binding.location.visibility = View.GONE
        } else binding.location.text = carModel.city

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

        val rating = "(${carModel.gradeScore.formatAmount()})"
        binding.ratingLevel.text = rating

        binding.carImage.load(carModel.imageUrl) {
            if (carModel.memoryCacheKey != null) placeholderMemoryCacheKey(carModel.memoryCacheKey)
            crossfade(true)
            allowHardware(true)
            listener(
                onSuccess = { _, result ->
                    Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                        val context = binding.root.context
                        val bgColor = palette?.getDarkVibrantColor(
                            ContextCompat.getColor(
                                context, R
                                    .color.featured_car_bg
                            )
                        )
                        if (bgColor != null) {
                            binding.carImage.setBackgroundColor(bgColor)
                        }

                        startPostponedEnterTransition()
                    }
                }
            )
        }
    }

    private fun setIsFavourite() {
        binding.favouriteIcon.setImageResource(
            if (carModel.isFavourite) R.drawable.favourite_active
            else R.drawable.favourite_normal
        )
    }

    override fun progressBarView(): View = binding.progressBar

    override fun onPause() {
        super.onPause()
        if (binding.carVideo.visibility == View.VISIBLE) binding.carVideo.pause()
    }

    override fun onResume() {
        super.onResume()
        if (binding.carVideo.visibility == View.VISIBLE)
            binding.carVideo.resume()
    }
}