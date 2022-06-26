package app.africa.autocheck.core.framework.utils

import app.africa.autocheck.core.framework.CustomLiveData
import app.africa.autocheck.core.framework.data.AutoState
import app.africa.autocheck.core.framework.remote.GenericResponse
import app.africa.autocheck.core.framework.retrofit.ApiResponse
import java.text.DecimalFormat


fun Double.format(): String {
    val decimalFormat = DecimalFormat("#,###,###")
    val formatted: String = decimalFormat.format(this)
    return if (formatted.startsWith(".")) "0.00" else formatted
}

fun Double.formatAmount(): String {
    val decimalAmountFormat = DecimalFormat("#,###,###.00")
    val formatted: String = decimalAmountFormat.format(this)
    return if (formatted.startsWith(".")) "0.00" else formatted
}

fun Int.format(): String? {
    val numberFormat = DecimalFormat("#,###,###")
    return numberFormat.format(this.toLong())
}

/* Error */
fun <T : Any> GenericResponse<Any>.handleError(): GenericResponse<T> {
    return when (val res = this) {
        is ApiResponse.ApiError -> {
            ApiResponse.ApiError(
                res.body,
                res.code
            )
        }
        is ApiResponse.NetworkError -> {
            ApiResponse.NetworkError(res.error)
        }
        is ApiResponse.UnknownError -> {
            ApiResponse.UnknownError(res.error, res.code)
        }
        else -> ApiResponse.UnknownError(null)
    }
}

fun <T : Any> CustomLiveData<AutoState>.postError(res: GenericResponse<T>) {
    when (res) {
        is ApiResponse.ApiError -> this.postValue(
            AutoState.Error(Throwable(res.body.message))
        )
        is ApiResponse.NetworkError -> this.postValue(
            AutoState.Error(res.error)
        )
        is ApiResponse.UnknownError -> this.postValue(
            AutoState.Error(res.error)
        )
        else -> {}
    }
}
