package app.africa.autocheck.core.data.popular.repo

import app.africa.autocheck.core.data.cars.CarDetails
import app.africa.autocheck.core.data.cars.CarMedia
import app.africa.autocheck.core.data.cars.remote.CarsRemoteSource
import app.africa.autocheck.core.data.popular.PopularMake
import app.africa.autocheck.core.data.popular.remote.PopularMakesRemoteSource
import app.africa.autocheck.core.framework.remote.GenericResponse
import app.africa.autocheck.core.framework.retrofit.ApiResponse
import app.africa.autocheck.core.framework.utils.handleError
import kotlinx.coroutines.coroutineScope
import org.koin.core.component.inject

class AutoRepository: AutoRepositorySource {

    private val popularRemote: PopularMakesRemoteSource by inject()
    private val carsRemote: CarsRemoteSource by inject()

    override suspend fun getPopularMakes(): GenericResponse<List<PopularMake>> = coroutineScope{
        val res = popularRemote.fetchAll()
        if (res is ApiResponse.Success){
            return@coroutineScope ApiResponse.Success(res.body.makeList)
        }
        else return@coroutineScope res.handleError()
    }

    override suspend fun getCars(pageNumber: Int, pageSize: Int) = coroutineScope {
        return@coroutineScope carsRemote.fetchCars(pageSize, pageNumber)
    }

    override suspend fun fetchCarDetails(carId: String): GenericResponse<CarDetails> =
        coroutineScope {
        return@coroutineScope carsRemote.fetchCarDetails(carId)
    }

    override suspend fun fetchCarMedia(carId: String): GenericResponse<List<CarMedia>> =
        coroutineScope {
            val res = carsRemote.fetchCarMedia(carId)
            if (res is ApiResponse.Success) {
                return@coroutineScope ApiResponse.Success(res.body.carMediaList)
            }
            return@coroutineScope res.handleError()
    }
}