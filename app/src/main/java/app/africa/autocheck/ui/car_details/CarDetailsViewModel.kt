package app.africa.autocheck.ui.car_details

import androidx.lifecycle.viewModelScope
import app.africa.autocheck.core.data.cars.Car
import app.africa.autocheck.core.data.cars.CarDetails
import app.africa.autocheck.core.data.cars.CarMedia
import app.africa.autocheck.core.framework.CustomLiveData
import app.africa.autocheck.core.framework.data.AutoState
import app.africa.autocheck.core.framework.retrofit.ApiResponse
import app.africa.autocheck.core.framework.ui.BaseViewModel
import app.africa.autocheck.core.framework.utils.postError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class CarDetailsViewModel : BaseViewModel(), CarDetailsContract.ViewModel{

    var carDetails: CarDetails? = null
    lateinit var car: Car
    val mediaList = mutableListOf<CarMedia>()
    var selectedMedia : CarMedia? = null
    val description = mutableListOf<VehicleDescription>()

    val loadMediaState = CustomLiveData<AutoState>()
    val loadDetailsState = CustomLiveData<AutoState>()
    val displayMediaState = CustomLiveData<AutoState>()

    fun loadMedia() {
        loadMediaState.postValue(AutoState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            val res = repo.fetchCarMedia(car.id)
            if (res is ApiResponse.Success) {
                mediaList.apply {
                    clear()
                    add(CarMedia(UUID.randomUUID().toString(), name = "Default", url = car
                        .imageUrl?:"", type = "image/*"))
                    addAll(res.body)
                }

                selectedMedia = mediaList[0]
                selectedMedia?.position = 0
                loadMediaState.postValue(AutoState.Success)
            }
            else loadMediaState.postError(res)
        }
    }

    fun loadCarDetails() {
        loadDetailsState.postValue(AutoState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            //delay(100)
            val res = repo.fetchCarDetails(car.id)
            if (res is ApiResponse.Success) {
                carDetails = res.body

                loadDetailsState.postValue(AutoState.Success)
            }
            else loadDetailsState.postError(res)
        }
    }

    override fun descriptionCount(): Int = description.size

    override fun bindDescriptionViewHolder(
        holder: CarDetailsContract.VehicleDescriptionItem,
        position: Int
    ) {
        holder.bind(description[position])
    }

    override fun mediaCount(): Int = mediaList.size

    override fun bindMediaViewHolder(holder: CarDetailsContract.CarMediaItem, position: Int) {
        holder.bind(mediaList[position])
    }

    override fun loadMediaAt(position: Int) {
        selectedMedia = mediaList[position]
        selectedMedia?.position = position
        displayMediaState.postValue(AutoState.Success)
    }

}
