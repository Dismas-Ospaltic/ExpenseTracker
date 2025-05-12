package com.st11.expensetracker.viewmodel

//WishlistViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.st11.expensetracker.model.WishlistEntity
import com.st11.expensetracker.repository.WishlistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WishlistViewModel(private val wishlistRepository: WishlistRepository) : ViewModel() {

    // Holds the list of wishlist
    private val _wishs = MutableStateFlow<List<WishlistEntity>>(emptyList())
    val wishs: StateFlow<List<WishlistEntity>> = _wishs

//    // Holds the list of debt Ids for a specific user
//    private val _debtsId = MutableStateFlow<List<DebtEntity>>(emptyList())
//    val debtsId: StateFlow<List<DebtEntity>> = _debtsId

//    private val _debtsId = MutableStateFlow<List<String>>(emptyList())
//    val debtsId: StateFlow<List<String>> = _debtsId

  init {
      getAllWishlist()
  }



//    // Holds a single debt item amount Rem
//    private val _debtAmountRem = MutableStateFlow<DebtEntity?>(null)
//    val debtAmountRem: StateFlow<DebtEntity?> = _debtAmountRem

    /**
     * Fetch all wishes
     */
    private fun getAllWishlist() {
        viewModelScope.launch {
            wishlistRepository.getAllWishlist().collectLatest { debtList ->
                _wishs.value = debtList
            }
        }
    }


    /**
     * Fetch all debts id for a specific user
     */
//    fun getAllDebtsId(userId: String) {
//        viewModelScope.launch {
//            debtRepository.getAllDebtId(userId).collectLatest { debtList ->
//                _debtsId.value = debtList
//            }
//        }
//    }



    /**
     * Insert a new wish record
     */
    fun insertWish(wish: WishlistEntity) {
        viewModelScope.launch {
            wishlistRepository.insertWish(wish)
        }
    }

    /**
     * Update an existing wish record
     */
    fun updateDebt(wish: WishlistEntity) {
        viewModelScope.launch {
            wishlistRepository.updateWish(wish)
        }
    }

    /**
     * Update the status of a wishlist
     */
    fun updateWishStatus(wishId: String, newStatus: String) {
        viewModelScope.launch {
            val success = wishlistRepository.updateWishStatus(wishId, newStatus)
            if (success) {
                // Refresh debt list if update is successful
                _wishs.value = _wishs.value.map { wish ->
                    if (wish.wishId == wishId) wish.copy(wishStatus = newStatus) else wish
                }
            }
        }
    }


    private val _totalWishlistAmount = MutableStateFlow(0.0f)
    val totalWishlistAmount: StateFlow<Float> = _totalWishlistAmount

    fun getAllTotalWishlistAmount() {
        viewModelScope.launch {
            wishlistRepository.getAllTotalWishlistAmount().collectLatest { total ->
                _totalWishlistAmount.value = total
            }
        }
    }


    private val _totalNoWishlist = MutableStateFlow(0)
    val totalNoWishlist : StateFlow<Int> = _totalNoWishlist

    fun getAllTotalWishlist() {
        viewModelScope.launch {
            wishlistRepository.getAllTotalWishlist().collectLatest { total ->
                _totalNoWishlist.value = total
            }
        }
    }



}