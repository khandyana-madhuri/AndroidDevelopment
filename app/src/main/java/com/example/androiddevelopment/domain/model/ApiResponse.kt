package com.example.androiddevelopment.domain.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val id: String,
    val name: String,
    val data: ItemData?
)

data class ItemData(
    @SerializedName("Color")
    val colorUpper: String?=null,
    @SerializedName("color")
    val color: String? = null,
    @SerializedName("capacity") val capacityLow: String?= null,
    @SerializedName("capacity GB") val capacityGB: Int? = null,
    @SerializedName("Capacity") val capacityUpper: String?= null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("Price")
    val priceUpper: Double?= null,
    @SerializedName("generation")
    val generation: String? = null,
    @SerializedName("Generation")
    val generationUpper: String?=null,
    val year: Int? = null,
    @SerializedName("CPU model") val cpuModel: String? = null,
    @SerializedName("Hard disk size") val hardDiskSize: String? = null,
    @SerializedName("Strap Colour") val strapColour: String? = null,
    @SerializedName("Case Size") val caseSize: String? = null,
    @SerializedName("Screen size") val screenSize: Double? = null,
    val description: String? = null,
    @SerializedName("Description")
    val descriptionUpper: String?= null
)

