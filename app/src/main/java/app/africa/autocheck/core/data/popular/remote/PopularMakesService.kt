package app.africa.autocheck.core.data.popular.remote

import app.africa.autocheck.core.data.popular.PopularMake
import app.africa.autocheck.core.framework.remote.BaseApiMakesResponse
import app.africa.autocheck.core.framework.remote.GenericResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PopularMakesService {

    @GET("inventory/make")
    suspend fun fetchAll(@QueryMap options: Map<String, String>) :
            GenericResponse<BaseApiMakesResponse<PopularMake>>

}