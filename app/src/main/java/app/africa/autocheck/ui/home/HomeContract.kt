package app.africa.autocheck.ui.home

import app.africa.autocheck.core.data.cars.Car
import app.africa.autocheck.core.data.popular.PopularMake
import coil.memory.MemoryCache

interface HomeContract {

    interface ViewModel {
        fun makesCount(): Int
        fun bindMakeViewHolder(holder: PopularMakeItem, position: Int)
        fun onMakeSelected(position: Int)

        fun carsCount(): Int
        fun bindCarViewHolder(holder: CarItem, position: Int)
        fun onCarSelected(position: Int, memoryCacheKey: MemoryCache.Key?)
        fun onFavouriteCar(position: Int)
    }

    interface PopularMakeItem {
        fun onBind(model: PopularMake)
    }

    interface CarItem {
        fun onBind(model: Car)
    }

}

