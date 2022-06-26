package app.africa.autocheck.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.africa.autocheck.databinding.ListPopularCarsBinding

class CarListAdapter (
    val viewModel: HomeContract.ViewModel,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<CarListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarListHolder {
        return CarListHolder(ListPopularCarsBinding.inflate(inflater, parent, false), viewModel)
    }

    override fun onBindViewHolder(holder: CarListHolder, position: Int) {
        viewModel.bindCarViewHolder(holder, position)
    }

    override fun getItemCount(): Int = viewModel.carsCount()
}