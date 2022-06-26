package app.africa.autocheck.core.data.popular

import java.io.Serializable

data class PopularMakes(
    val id: String, val name: String, val imageUrl: String?
) : Serializable
