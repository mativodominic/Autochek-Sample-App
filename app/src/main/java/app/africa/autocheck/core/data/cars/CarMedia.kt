package app.africa.autocheck.core.data.cars

import java.io.Serializable

data class CarMedia(
    val id: String,
    val name: String,
    val url: String,
    val type: String?
) : Serializable
