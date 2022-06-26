package app.africa.autocheck.core.data.cars.remote

import app.africa.autocheck.core.data.cars.Car
import app.africa.autocheck.core.data.cars.CarDetails
import app.africa.autocheck.core.data.cars.CarMedia
import app.africa.autocheck.core.framework.remote.*
import kotlinx.coroutines.coroutineScope

interface CarsRemoteSource {

    suspend fun fetchCars(pageSize : Int, page: Int) :
            GenericResponse<BaseApiCarResponse<Car>>

    suspend fun fetchCarDetails(carId: String) :
            GenericResponse<CarDetails>

    suspend fun fetchCarMedia(carId: String) : GenericResponse<BaseApiMediaResponse<CarMedia>>

}

class CarsRemote : BaseRemoteSource<CarsService>(), CarsRemoteSource {

    init {
        setParams(CarsService::class.java)
    }

    override suspend fun fetchCars(pageSize : Int, page: Int):
            GenericResponse<BaseApiCarResponse<Car>> = coroutineScope{
        val options = mutableMapOf<String , String>()
        options["pageSize"] = pageSize.toString()
        options["pageNumber"] = page.toString()
        options["country"] = "KE"

        return@coroutineScope build().service!!.fetchCars(options)
    }

    override suspend fun fetchCarDetails(carId: String): GenericResponse<CarDetails> = coroutineScope{
        return@coroutineScope build().service!!.fetchCarDetails(carId)
    }

    override suspend fun fetchCarMedia(carId: String):
            GenericResponse<BaseApiMediaResponse<CarMedia>> = coroutineScope {
        return@coroutineScope build().service!!.fetchCarMedia(carId)
    }

}