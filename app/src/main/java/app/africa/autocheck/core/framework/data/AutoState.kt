package app.africa.autocheck.core.framework.data

sealed class AutoState {
    object Loading : AutoState()
    object NoData: AutoState()
    data class Error(val error: Throwable?): AutoState()
    object Success: AutoState()
}
