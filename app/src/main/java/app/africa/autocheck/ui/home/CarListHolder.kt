package app.africa.autocheck.ui.home

import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import app.africa.autocheck.R
import app.africa.autocheck.core.data.cars.Car
import app.africa.autocheck.core.framework.utils.format
import app.africa.autocheck.core.framework.utils.formatAmount
import app.africa.autocheck.databinding.ListPopularCarsBinding
import coil.load
import coil.result

class CarListHolder(
    private val binding: ListPopularCarsBinding,
    private val viewModel: HomeContract.ViewModel
) : RecyclerView.ViewHolder(binding.root), HomeContract.CarItem {

    init {
        binding.favouriteIcon.setOnClickListener {
            viewModel.onFavouriteCar(bindingAdapterPosition)
        }

        binding.root.setOnClickListener {
            viewModel.onCarSelected(bindingAdapterPosition, binding.carImage.result?.request?.memoryCacheKey)
        }
    }

    override fun onBind(model: Car) {
        binding.nameOfCar.text = model.title
        binding.yearOfManufacture.text = model.year.toString()

        val mileage = "${model.mileage.format()} ${model.mileageUnit}"
        binding.mileage.text = mileage
        binding.location.text = model.city

        val amount = "$${model.marketplacePrice.format()}"
        binding.costOfCar.text = amount

        val rating = "(${ model.gradeScore.formatAmount() })"
        binding.ratingLevel.text = rating

        binding.carImage.load(model.imageUrl) {
            crossfade(true)
            allowHardware(false)
            listener(
                onSuccess = { _, result ->
                    Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                        val context = binding.root.context
                        val bgColor = palette?.getDarkMutedColor(ContextCompat.getColor
                            (context, R
                            .color.featured_car_bg))
                        if (bgColor != null) {
                            binding.carImage.setBackgroundColor(bgColor)
                        }
                    }
                }
            )
        }

        binding.favouriteIcon.setImageResource(
            if (model.isFavourite) R.drawable.favourite_active
            else R.drawable.favourite_normal
        )
    }

}