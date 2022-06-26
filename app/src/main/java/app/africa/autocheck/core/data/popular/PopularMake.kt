package app.africa.autocheck.core.data.popular

import java.io.Serializable

data class PopularMake(
    val id: String, val name: String, val imageUrl: String?
) : Serializable
