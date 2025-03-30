package com.example.androiddevelopment.data.repository

import com.example.androiddevelopment.data.ProductEntity
import com.example.androiddevelopment.data.remote.ApiService
import com.example.androiddevelopment.data.room.dao.ProductDao
import com.example.androiddevelopment.domain.model.ApiResponse
import com.example.androiddevelopment.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(private val productDao: ProductDao, private val apiService: ApiService) : ProductRepository {
    override val allProducts: Flow<List<ProductEntity>> get() = productDao.getAllProducts()

    override suspend fun insert(product: ProductEntity) {
        productDao.insertProduct(product)
    }

    override suspend fun insertAll(products: List<ProductEntity>) {
        productDao.insertAll(products)
    }

    override suspend fun update(product: ProductEntity) {
        productDao.updateProduct(product)
    }

    override suspend fun delete(product: ProductEntity) {
        productDao.deleteProduct(product)
    }

    override suspend fun deleteAll() {
        productDao.deleteAllProducts()
    }

    override suspend fun getProductById(id: String): ProductEntity? {
        return productDao.getProductById(id)
    }

    override suspend fun getProducts(): List<ApiResponse> {
        return apiService.getObjects()
    }

}