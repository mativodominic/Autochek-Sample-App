package app.africa.autocheck.ui.home

import androidx.lifecycle.viewModelScope
import app.africa.autocheck.core.data.cars.Car
import app.africa.autocheck.core.data.popular.PopularMake
import app.africa.autocheck.core.framework.CustomLiveData
import app.africa.autocheck.core.framework.data.AutoReloadState
import app.africa.autocheck.core.framework.data.AutoState
import app.africa.autocheck.core.framework.remote.Pagination
import app.africa.autocheck.core.framework.retrofit.ApiResponse
import app.africa.autocheck.core.framework.ui.BaseViewModel
import app.africa.autocheck.core.framework.utils.postError
import coil.memory.MemoryCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: BaseViewModel(), HomeContract.ViewModel {

    private var pagination: Pagination? = null
    val makes = mutableListOf<PopularMake>()
    val cars = mutableListOf<Car>()
    var carPageNumber = 1
    val carPageSize = 20

    val loadMakesState = CustomLiveData<AutoState>()
    val loadMoreCarsState = CustomLiveData<AutoState>()
    val loadCarsState = CustomLiveData<AutoState>()
    val reloadState = CustomLiveData<AutoReloadState<Boolean>>()
    val viewDetailsState = CustomLiveData<Car>()

    fun loadPopularMakes() {
        if (makes.isNotEmpty()) return
        loadMakesState.postValue(AutoState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val res = repo.getPopularMakes()
            if (res is ApiResponse.Success) {
                makes.apply {
                    clear()
                    addAll(res.body)
                }

                loadMakesState.postValue(AutoState.Success)
            }
            else loadMakesState.postError(res)
        }
    }

    /**
     * Loads the first page of cars
     */
    fun loadCars() {
        if (cars.isNotEmpty()) return
        loadCarsState.postValue(AutoState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val res = repo.getCars(carPageNumber, carPageSize)
            if (res is ApiResponse.Success) {
                cars.apply {
                    clear()
                    addAll(res.body.result)
                }

                pagination = res.body.pagination
                loadCarsState.postValue(AutoState.Success)
            }
            else loadCarsState.postError(res)
        }
    }

    /**
     * Loads the first page of cars
     */
    fun loadMoreCars() {
        // If there's no pagination object available, load the first page
        if (pagination == null) return loadCars()

        // Just in case we've retrieved all the available pages, return
        val totalFetched = pagination?.pageSize!! * pagination?.currentPage!!
        if (totalFetched >= pagination!!.total) return

        loadMoreCarsState.postValue(AutoState.Loading)
        viewModelScope.launch {
            val res = repo.getCars(pagination?.currentPage!! +1, carPageSize)
            if (res is ApiResponse.Success) {

                cars.apply {
                    addAll(res.body.result)
                }

                pagination = res.body.pagination
                loadMoreCarsState.postValue(AutoState.Success)
            }
            else loadMoreCarsState.postError(res)
        }
    }

    override fun makesCount(): Int = makes.size

    override fun bindMakeViewHolder(holder: HomeContract.PopularMakeItem, position: Int) {
        holder.onBind(makes[position])
    }

    override fun onMakeSelected(position: Int) {}

    override fun carsCount(): Int = cars.size

    override fun bindCarViewHolder(holder: HomeContract.CarItem, position: Int) {
        holder.onBind(cars[position])
    }

    override fun onCarSelected(position: Int, memoryCacheKey: MemoryCache.Key?) {
        val selectedCar = cars[position]
        selectedCar.memoryCacheKey = memoryCacheKey
        viewDetailsState.postValue(selectedCar)
    }

    override fun onFavouriteCar(position: Int) {
        val car = cars[position]
        car.isFavourite = !car.isFavourite

        cars[position] = car
        reloadState.postValue(AutoReloadState.Success(body = true, position))
    }

}