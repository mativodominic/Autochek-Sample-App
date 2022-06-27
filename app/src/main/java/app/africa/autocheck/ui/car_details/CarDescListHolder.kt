package app.africa.autocheck.ui.car_details

import androidx.recyclerview.widget.RecyclerView
import app.africa.autocheck.databinding.ListVehicleDescriptionBinding

class CarDescListHolder(
    private val binding: ListVehicleDescriptionBinding
) : RecyclerView.ViewHolder(binding.root), CarDetailsContract.VehicleDescriptionItem {

    override fun bind(model: VehicleDescription) {
        binding.descTitle.text = model.title
        binding.descValue.text = model.titleValue
    }

}