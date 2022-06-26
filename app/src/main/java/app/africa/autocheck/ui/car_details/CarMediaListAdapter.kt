package app.africa.autocheck.ui.car_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.africa.autocheck.databinding.ListCarMediaBinding
import app.africa.autocheck.databinding.ListPopularCarsBinding

class CarMediaListAdapter (
    val viewModel: CarDetailsContract.ViewModel,
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<CarMediaListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarMediaListHolder {
        return CarMediaListHolder(ListCarMediaBinding.inflate(inflater, parent, false),
            viewModel)
    }

    override fun onBindViewHolder(holder: CarMediaListHolder, position: Int) {
        viewModel.bindMediaViewHolder(holder, position)
    }

    override fun getItemCount(): Int = viewModel.mediaCount()
}