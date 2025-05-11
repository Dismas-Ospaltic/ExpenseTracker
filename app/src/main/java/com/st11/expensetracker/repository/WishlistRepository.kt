package com.st11.expensetracker.repository


import com.st11.expensetracker.data.local.WishDao
import com.st11.expensetracker.model.WishlistEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WishlistRepository(private val wishDao: WishDao) {

    //    val allDebt: Flow<List<DebtEntity>> = debtDao.getAllDebt(userId)
    fun getAllWishlist(): Flow<List<WishlistEntity>> = wishDao.getAllWishlist()


    suspend fun insertWish(wish: WishlistEntity) {
        wishDao.insertWish(wish)
    }

    suspend fun updateWish(wish: WishlistEntity) {
        wishDao.updateWish(wish)
    }


    fun getAllTotalWishlist(): Flow<Int> {
        return wishDao.getAllTotalWishlist()
            .map { total -> total ?: 0 }  // Convert NULL to 0.0
    }


    fun getAllTotalWishlistAmount(): Flow<Float> {
        return wishDao.getAllTotalWishlistAmount()
            .map { total -> total ?: 0.0f }  // Convert NULL to 0.0
    }



    suspend fun updateWishStatus(wishId: String, newStatus: String): Boolean {
        val rowsUpdated = wishDao.updateWishStatus(wishId, newStatus) ?: 0
        return rowsUpdated > 0
    }







}
