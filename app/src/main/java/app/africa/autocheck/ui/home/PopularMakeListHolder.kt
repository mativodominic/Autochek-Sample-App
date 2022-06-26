package app.africa.autocheck.ui.home

import androidx.recyclerview.widget.RecyclerView
import app.africa.autocheck.R
import app.africa.autocheck.core.data.popular.PopularMake
import app.africa.autocheck.databinding.ListPopularMakesBinding
import com.squareup.picasso.Picasso

class PopularMakeListHolder(
    private val binding: ListPopularMakesBinding,
    private val viewModel: HomeContract.ViewModel
) : RecyclerView.ViewHolder(binding.root), HomeContract.PopularMakeItem {

    init {
        binding.root.setOnClickListener { viewModel.onMakeSelected(bindingAdapterPosition) }
    }

    override fun onBind(model: PopularMake) {
        binding.nameOfMake.text = model.name

        model.imageUrl?.let { images ->
            if (images.isNotEmpty()) {
                Picasso
                    .get()
                    .load(images)
                    .resizeDimen(R.dimen.dimen_56, R.dimen.dimen_56)
                    .centerInside()
                    .onlyScaleDown()
                    .into(binding.makeImage)
            }
        }
    }

}