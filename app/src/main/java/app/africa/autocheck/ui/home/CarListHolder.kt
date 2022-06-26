package app.africa.autocheck.ui.home

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import app.africa.autocheck.R
import app.africa.autocheck.core.data.cars.Car
import app.africa.autocheck.core.framework.utils.format
import app.africa.autocheck.core.framework.utils.formatAmount
import app.africa.autocheck.databinding.ListPopularCarsBinding
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso

class CarListHolder(
    private val binding: ListPopularCarsBinding,
    private val viewModel: HomeContract.ViewModel
) : RecyclerView.ViewHolder(binding.root), HomeContract.CarItem {

    init {
        binding.favouriteIcon.setOnClickListener {
            viewModel.onFavouriteCar(bindingAdapterPosition)
        }

        binding.root.setOnClickListener { viewModel.onCarSelected(bindingAdapterPosition) }
    }

    override fun onBind(model: Car) {
        binding.nameOfCar.text = model.title
        binding.yearOfManufacture.text = model.year.toString()

        val mileage = "${model.mileage.format()} ${model.mileageUnit}"
        binding.mileage.text = mileage
        binding.location.text = model.city

        val amount = model.marketplacePrice.format()
        binding.costOfCar.text = amount

        val rating = "(${ model.gradeScore?.formatAmount() })"
        binding.ratingLevel.text = rating

        if (model.imageUrl?.endsWith(".svg") == true) {
            GlideToVectorYou
                .init()
                .with(binding.root.context)
                .setPlaceHolder(R.drawable.ic_placeholder, R.drawable.ic_placeholder)
                .load(Uri.parse(model.imageUrl), binding.carImage)
        }
        else {
            model.imageUrl?.let { images ->
                if (images.isNotEmpty()) {
                    Picasso
                        .get()
                        .load(images)
                        .into(binding.carImage)
                }
            }
        }

        binding.favouriteIcon.setImageResource(
            if (model.isFavourite) R.drawable.favourite_active
            else R.drawable.favourite_normal
        )
    }

}