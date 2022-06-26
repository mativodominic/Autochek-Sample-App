package app.africa.autocheck.core.data.cars.remote

import app.africa.autocheck.core.data.cars.Car
import app.africa.autocheck.core.framework.remote.BaseApiCarResponse
import app.africa.autocheck.core.framework.remote.GenericResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface CarsService {

    @GET("inventory/car/search")
    suspend fun fetchCars(@QueryMap options: Map<String, String>) :
            GenericResponse<BaseApiCarResponse<Car>>

    @GET("inventory/car/{carId}")
    suspend fun fetchCarDetails(@Path("carId") carId: String) :
            GenericResponse<BaseApiCarResponse<Car>>


}