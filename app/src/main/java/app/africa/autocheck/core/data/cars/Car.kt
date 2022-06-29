package app.africa.autocheck.core.data.cars

import coil.memory.MemoryCache
import java.io.Serializable

data class Car(
    val id: String,
    val title: String?,
    val imageUrl: String?,
    val year: Int,
    val city: String?,
    val state: String?,
    val gradeScore: Double = 0.0,
    val hasWarranty: Boolean,
    val marketplacePrice: Int,
    val marketplaceOldPrice: Int,
    val hasFinancing: Boolean,
    val mileage: Int,
    val mileageUnit: String?,
    val installment: Int,
    val depositReceived: Boolean,
    val loanValue: Double,
    val websiteUrl: String?,
    val bodyTypeId: Int,
    val sold: Boolean,
    val transmission: String?
) : Serializable {
    var memoryCacheKey: MemoryCache.Key? = null
    var isFavourite: Boolean = false
}
