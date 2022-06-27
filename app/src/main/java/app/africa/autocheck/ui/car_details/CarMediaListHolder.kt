package app.africa.autocheck.ui.car_details

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import app.africa.autocheck.R
import app.africa.autocheck.core.data.cars.CarMedia
import app.africa.autocheck.databinding.ListCarMediaBinding
import coil.load

class CarMediaListHolder(
    private val binding: ListCarMediaBinding,
    private val viewModel: CarDetailsContract.ViewModel
) : RecyclerView.ViewHolder(binding.root), CarDetailsContract.CarMediaItem {

    init {
        binding.root.setOnClickListener { viewModel.loadMediaAt(bindingAdapterPosition) }
    }

    override fun bind(model: CarMedia) {
        binding.playVideo.visibility = if (model.type?.startsWith("video") == true) View
            .VISIBLE else View.GONE

        binding.mediaImage.load(model.url) {
            crossfade(true)

            allowHardware(false)
            listener(
                onSuccess = { _, result ->
                    Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                        val context = binding.root.context
                        val bgColor = palette?.getDarkMutedColor(
                            ContextCompat.getColor(
                                context, R
                                    .color.featured_car_bg
                            )
                        )
                        if (bgColor != null) {
                            binding.mediaImage.setBackgroundColor(bgColor)
                        }
                    }
                }
            )
        }

    }

}