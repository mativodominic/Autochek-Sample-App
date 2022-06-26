package app.africa.autocheck.ui.home

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.africa.autocheck.databinding.ListPopularMakesBinding

class PopularMakeListAdapter(
    val viewModel: HomeContract.ViewModel,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<PopularMakeListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMakeListHolder {
        return PopularMakeListHolder(
            ListPopularMakesBinding.inflate(inflater, parent, false), viewModel)
    }

    override fun onBindViewHolder(holder: PopularMakeListHolder, position: Int) {
        viewModel.bindMakeViewHolder(holder, position)
    }

    override fun getItemCount(): Int = viewModel.makesCount()
}