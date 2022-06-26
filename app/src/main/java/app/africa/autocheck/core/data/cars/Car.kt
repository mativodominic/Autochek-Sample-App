package app.africa.autocheck.core.data.cars

import java.io.Serializable

data class Car(
    val id: String,
    val title: String?,
    val imageUrl: String?,
    val year: Int,
    val city: String?,
    val state: String?,
    val hasWarranty: Boolean,
    val marketplacePrice: Int,
    val marketplaceOldPrice: Int,
    val hasFinancing: Boolean,
    val mileage: Int,
    val mileageUnit: String?,
    val installment: Int,
    val depositReceived: Boolean,
    val loanValue: Int,
    val websiteUrl: String?,
    val bodyTypeId: Int,
    val sold: Boolean,
    val transmission: String?
) : Serializable
