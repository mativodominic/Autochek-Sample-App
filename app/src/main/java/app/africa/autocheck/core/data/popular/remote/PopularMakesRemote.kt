package app.africa.autocheck.core.data.popular.remote

import app.africa.autocheck.core.data.popular.PopularMake
import app.africa.autocheck.core.framework.remote.BaseApiMakesResponse
import app.africa.autocheck.core.framework.remote.BaseRemoteSource
import app.africa.autocheck.core.framework.remote.GenericResponse
import kotlinx.coroutines.coroutineScope

interface PopularMakesRemoteSource {

    suspend fun fetchAll() : GenericResponse<BaseApiMakesResponse<PopularMake>>

}

class PopularMakesRemote : BaseRemoteSource<PopularMakesService>(), PopularMakesRemoteSource {

    init {
        setParams(PopularMakesService::class.java)
    }

    override suspend fun fetchAll(): GenericResponse<BaseApiMakesResponse<PopularMake>> = coroutineScope{
        val options = mutableMapOf<String , String>()
        options["popular"] = true.toString()
        options["pageSize"] = 20.toString()

        return@coroutineScope build().service!!.fetchAll(options)
    }

}