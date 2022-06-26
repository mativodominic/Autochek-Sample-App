package app.africa.autocheck.core.data.cars

import java.io.Serializable

data class CarBodyType(
    val id: String,
    val name: String,
    val imageUrl: String?
) : Serializable
