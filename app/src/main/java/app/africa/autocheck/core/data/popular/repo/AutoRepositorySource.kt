package app.africa.autocheck.core.data.popular.repo

import app.africa.autocheck.core.data.cars.Car
import app.africa.autocheck.core.data.cars.CarDetails
import app.africa.autocheck.core.data.cars.CarMedia
import app.africa.autocheck.core.data.popular.PopularMake
import app.africa.autocheck.core.framework.remote.BaseApiCarResponse
import app.africa.autocheck.core.framework.remote.GenericResponse
import org.koin.core.component.KoinComponent

interface AutoRepositorySource: KoinComponent {

    /**
     * Fetch the list of the popular makes
     */
    suspend fun getPopularMakes() : GenericResponse<List<PopularMake>>

    /**
     * Fetch the list of cars.
     *
     * @param pageSize The number of records to rerieve per page
     * @param pageNumber The page number to retrieve
     */
    suspend fun getCars(pageNumber: Int = 1, pageSize: Int = 1): GenericResponse<BaseApiCarResponse<Car>>

    /**
     * Retrieve additional details about the car
     * @param carId The identifier of the car
     */
    suspend fun fetchCarDetails(carId: String) : GenericResponse<CarDetails>

    /**
     * Retrieve media linked to the car
     * @param carId The identifier of the car
     */
    suspend fun fetchCarMedia(carId: String) : GenericResponse<List<CarMedia>>

}