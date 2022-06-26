package app.africa.autocheck.ui.home

import android.app.Activity
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import app.africa.autocheck.R
import app.africa.autocheck.core.data.popular.PopularMake
import app.africa.autocheck.databinding.ListPopularMakesBinding
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener
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

        if (model.imageUrl?.endsWith(".svg") == true) {
            GlideToVectorYou
                .init()
                .with(binding.root.context)
                .setPlaceHolder(R.drawable.ic_placeholder, R.drawable.ic_placeholder)
                .load(Uri.parse(model.imageUrl), binding.makeImage)
        }
        else {
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

}