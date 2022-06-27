package app.africa.autocheck.core.framework.data

/**
 * @author Dominic Mativo
 */
sealed class AutoState {
    object Loading : AutoState()
    object NoData : AutoState()
    data class Error(val error: Throwable?) : AutoState()
    object Success : AutoState()
}

sealed class AutoReloadState<out T : Any> {
    data class Loading(val loadingPosition: Int = -1) : AutoReloadState<Nothing>()
    data class Error<T : Any>(val error: Throwable?, val position: Int) : AutoReloadState<T>()
    data class Success<T : Any>(
        val body: T? = null, val position: Int = -1
    ) : AutoReloadState<T>()
}

