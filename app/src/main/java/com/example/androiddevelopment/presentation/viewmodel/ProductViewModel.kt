package com.example.androiddevelopment.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevelopment.data.ProductEntity
import com.example.androiddevelopment.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _products = MutableStateFlow<List<ProductEntity>>(emptyList())
    val products: StateFlow<List<ProductEntity>> = _products.asStateFlow()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        loadAllProducts()
    }

    private fun loadAllProducts() {
        viewModelScope.launch {
            repository.allProducts.collect { products ->
                _products.value = products
            }
        }
    }

    fun insertProduct(product: ProductEntity) = viewModelScope.launch {
        try {
            repository.insert(product)
        } catch (e: Exception) {
            _errorMessage.value = "Insert failed: ${e.message}"
        }
    }

    fun updateProduct(product: ProductEntity) = viewModelScope.launch {
        try {
            repository.update(product)
        } catch (e: Exception) {
            _errorMessage.value = "Update failed: ${e.message}"
        }
    }

    fun deleteProduct(product: ProductEntity) = viewModelScope.launch {
        try {
            repository.delete(product)
        } catch (e: Exception) {
            _errorMessage.value = "Delete failed: ${e.message}"
        }
    }
    

}
