package app.africa.autocheck.core.framework.remote

import app.africa.autocheck.core.framework.retrofit.APIError
import app.africa.autocheck.core.framework.retrofit.ApiResponse
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import org.koin.core.component.KoinComponent

/**
 * @author Dominic Mativo
 */
typealias GenericResponse<S> = ApiResponse<S, APIError>

open class BaseRemoteSource <S>(private val gson: Gson = Gson()): KoinComponent {

    protected lateinit var serviceClass: Class<S>
    var service: S? = null

    fun build(): BaseRemoteSource<S> {
        service = api().createService(serviceClass)
        return this
    }

    fun setParams(mServiceClass: Class<S>){
        this.serviceClass = mServiceClass
    }

    fun <R> parse(json: JsonElement, c: Class<R>): R {
        return gson.fromJson(json, c)
    }

    fun <R> parse(json: JsonElement, type: TypeToken<R>): R? {
        return gson.fromJson(json, type.type)
    }

}