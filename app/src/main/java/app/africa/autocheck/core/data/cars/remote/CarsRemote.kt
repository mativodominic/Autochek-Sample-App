package app.africa.autocheck.core.data.cars.remote

import app.africa.autocheck.core.data.cars.Car
import app.africa.autocheck.core.data.popular.PopularMakes
import app.africa.autocheck.core.framework.remote.*
import kotlinx.coroutines.coroutineScope

interface CarsRemoteSource {

    suspend fun fetchCars(pageSize : Int, page: Int) :
            GenericResponse<BaseApiCarResponse<Car>>

}

class CarsRemote : BaseRemoteSource<CarsService>(), CarsRemoteSource {

    init {
        setParams(CarsService::class.java)
    }

    override suspend fun fetchCars(pageSize : Int, page: Int):
            GenericResponse<BaseApiCarResponse<Car>> = coroutineScope{
        val options = mutableMapOf<String , String>()
        options["pageSize"] = pageSize.toString()

        return@coroutineScope build().service!!.fetchCars(options)
    }

}