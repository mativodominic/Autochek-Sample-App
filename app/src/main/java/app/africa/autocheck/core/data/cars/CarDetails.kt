package app.africa.autocheck.core.data.cars

import java.io.Serializable

data class CarDetails(
    val id: String,
    val carName: String?,
    val imageUrl: String?,
    val year: Int,
    val mileage: Int,
    val isFeatured: Boolean,
    val marketplaceVisible: Boolean,

    val city: String?,
    val state: String?,
    val country: String?,
    val ownerType: String?,
    val gradeScore: Double?,

    val hasWarranty: Boolean,
    val marketplacePrice: Int,
    val marketplaceOldPrice: Int,
    val hasFinancing: Boolean,
    val mileageUnit: String?,

    val installment: Int,
    val depositReceived: Boolean,
    val loanValue: Int,
    val websiteUrl: String?,
    val bodyTypeId: Int,
    val sold: Boolean,
    val engineType: String,
    val transmission: String?,
    val createdAt: String?,
    val bodyType: CarBodyType?,
    val model: CarModel?
) : Serializable
