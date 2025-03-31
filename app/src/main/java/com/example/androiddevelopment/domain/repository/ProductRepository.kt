package com.example.androiddevelopment.domain.repository

import com.example.androiddevelopment.data.model.ProductEntity
import com.example.androiddevelopment.domain.model.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    val allProducts: Flow<List<ProductEntity>>

    suspend fun insert(product: ProductEntity)

    suspend fun update(product: ProductEntity)

    suspend fun delete(product: ProductEntity)

    suspend fun deleteAll()

    suspend fun getProducts() : List<ApiResponse>
}