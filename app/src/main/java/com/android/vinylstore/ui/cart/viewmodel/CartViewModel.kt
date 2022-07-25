package com.android.vinylstore.ui.cart.viewmodel

import androidx.lifecycle.*
import com.android.vinylstore.data.db.data_entity.CartItem
import com.android.vinylstore.data.repository.VinylsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel(private val vinylsRepository: VinylsRepository) : ViewModel() {
    fun addTestItem() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                vinylsRepository.addItemToCart(CartItem(0, "Moral Panic", "Nothing But Thieves", 1, 1))

            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                vinylsRepository.clearCart()
            }
        }
    }

    val cartItems = vinylsRepository.cartItems
}

@Suppress("UNCHECKED_CAST")
class CartViewModelFactory(private val vinylsRepository: VinylsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(vinylsRepository) as T
    }
}