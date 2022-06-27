package app.africa.autocheck.core.framework.remote

/**
 * @author Dominic Mativo
 */
data class BaseApiResponse<T>(var data: T)

data class BaseApiMakesResponse<T>(var makeList: List<T>)

data class BaseApiCarResponse<T>(var result: List<T>, var pagination: Pagination)

data class BaseApiMediaResponse<T>(var carMediaList: List<T>, var pagination: Pagination)

data class BaseApiNoResponse(var status: Int? = 200, var message: String? = null)

data class Pagination(
    val total: Int,
    val currentPage: Int,
    var pageSize: Int
)
