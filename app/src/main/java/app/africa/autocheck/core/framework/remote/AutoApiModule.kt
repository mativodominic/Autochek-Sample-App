package app.africa.autocheck.core.framework.remote

import app.africa.autocheck.BuildConfig
import app.africa.autocheck.core.framework.retrofit.ApiResponseAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun api(): AutoApiComponent = AutoApiModule

interface AutoApiComponent {

    /**
     * Perform a network request with {@link Retrofit}
     * @param serviceClass Class<Unit> The {@link Retrofit} interface service class for the particular request
     */
    fun <T> createService(
        serviceClass: Class<T>
    ): T

}

object AutoApiModule : AutoApiComponent {

    override fun <T> createService(serviceClass: Class<T>): T {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val clientBuilder = OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder()
            .client(clientBuilder.build())
            .addCallAdapterFactory(ApiResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BaseApi)
            .build()

        return retrofit.create(serviceClass)
    }
}