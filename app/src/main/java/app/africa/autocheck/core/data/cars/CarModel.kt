package app.africa.autocheck.core.data.cars

import java.io.Serializable

data class CarModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val wheelType: String?,
    val make: CarMake?,
    val popular: Boolean
) : Serializable
