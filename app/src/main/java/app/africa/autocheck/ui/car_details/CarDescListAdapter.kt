package app.africa.autocheck.ui.car_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.africa.autocheck.databinding.ListVehicleDescriptionBinding

class CarDescListAdapter(
    val viewModel: CarDetailsContract.ViewModel,
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<CarDescListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarDescListHolder {
        return CarDescListHolder(ListVehicleDescriptionBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: CarDescListHolder, position: Int) {
        viewModel.bindDescriptionViewHolder(holder, position)
    }

    override fun getItemCount(): Int = viewModel.descriptionCount()
}