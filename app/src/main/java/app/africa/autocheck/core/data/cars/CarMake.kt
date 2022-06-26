package app.africa.autocheck.core.data.cars

import java.io.Serializable

data class CarMake(
    val id: String,
    val name: String,
    val imageUrl: String?
) : Serializable
