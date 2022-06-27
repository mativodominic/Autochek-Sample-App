package app.africa.autocheck.core.framework.retrofit

import java.io.IOException

/**
 * @author Dominic Mativo
 */
sealed class ApiResponse<out T : Any, out U : Any> {
    data class Success<T : Any>(val body: T) : ApiResponse<T, Nothing>()

    /** Represents non-2xx responses */
    data class ApiError<U : Any>(val body: U, val code: Int) : ApiResponse<Nothing, U>()

    /** Represents Network errors and connectivity issues */
    data class NetworkError(val error: IOException) : ApiResponse<Nothing, Nothing>()

    /** Unexpected exceptions while processing response or requests, for instance exceptions
     * while parsing responses
     */
    data class UnknownError(val error: Throwable?, val code: Int? = null) : ApiResponse<Nothing, Nothing>()
}


data class APIError (
    var status: Int,
    var message: String?,
    var error: String? = "",
    var error_description: String? = ""
)