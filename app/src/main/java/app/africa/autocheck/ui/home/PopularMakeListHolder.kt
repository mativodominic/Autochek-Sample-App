package app.africa.autocheck.ui.home

import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import app.africa.autocheck.R
import app.africa.autocheck.core.data.popular.PopularMake
import app.africa.autocheck.databinding.ListPopularMakesBinding
import coil.load

class PopularMakeListHolder(
    private val binding: ListPopularMakesBinding,
    private val viewModel: HomeContract.ViewModel
) : RecyclerView.ViewHolder(binding.root), HomeContract.PopularMakeItem {

    init {
        binding.root.setOnClickListener { viewModel.onMakeSelected(bindingAdapterPosition) }
    }

    override fun onBind(model: PopularMake) {
        binding.nameOfMake.text = model.name
        binding.makeImage.load(model.imageUrl) {
            placeholder(R.drawable.ic_placeholder)
            crossfade(true)

            allowHardware(false)
            listener(
                onSuccess = { _, result ->
                    Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                        val context = binding.root.context
                        val bgColor = palette?.getDarkMutedColor(
                            ContextCompat.getColor
                            (context, R
                            .color.featured_car_bg))
                        if (bgColor != null) {
                            binding.makeImage.setBackgroundColor(bgColor)
                        }
                    }
                }
            )
        }

    }

}