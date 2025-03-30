package com.example.androiddevelopment.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androiddevelopment.domain.model.ApiResponse

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val color: String? = null,
    val capacity: String? = null,
    val price: Double? = null,
    val generation: String? = null,
    val year: Int? = null,
    val cpuModel: String? = null,
    val hardDiskSize: String? = null,
    val strapColour: String? = null,
    val caseSize: String? = null,
    val screenSize: Double? = null,
    val description: String? = null
) {
    companion object {
        fun fromApiResponse(apiResponse: ApiResponse): ProductEntity {
            val data = apiResponse.data
            return ProductEntity(
                id = apiResponse.id,
                name = apiResponse.name,
                color = data?.color ?: data?.colorUpper,
                capacity = data?.capacityLow ?: data?.capacityUpper ?: data?.capacityGB?.let { "$it GB" },
                price = data?.price ?: data?.priceUpper,
                generation = data?.generation ?: data?.generationUpper,
                year = data?.year,
                cpuModel = data?.cpuModel,
                hardDiskSize = data?.hardDiskSize,
                strapColour = data?.strapColour,
                caseSize = data?.caseSize,
                screenSize = data?.screenSize,
                description = data?.description ?: data?.descriptionUpper
            )
        }
    }
}
