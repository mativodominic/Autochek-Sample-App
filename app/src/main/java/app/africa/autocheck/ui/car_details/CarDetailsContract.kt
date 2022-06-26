package app.africa.autocheck.ui.car_details

import app.africa.autocheck.core.data.cars.CarMedia

interface CarDetailsContract {
    interface ViewModel {
        fun mediaCount() : Int
        fun bindMediaViewHolder(holder: CarMediaItem, position: Int)
        fun loadMediaAt(position: Int)
    }

    interface CarMediaItem {
        fun bind(model: CarMedia)
    }
}
