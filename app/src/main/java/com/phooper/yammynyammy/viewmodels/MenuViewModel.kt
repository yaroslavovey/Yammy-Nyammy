package com.phooper.yammynyammy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phooper.yammynyammy.data.models.Product
import com.phooper.yammynyammy.data.repositories.ProductsRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MenuViewModel(private val productsRepository: ProductsRepository) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun loadProducts(category: Int) = viewModelScope.launch(IO) {
        _products.postValue(productsRepository.getProductListByCategory(category.toString()))
    }

}