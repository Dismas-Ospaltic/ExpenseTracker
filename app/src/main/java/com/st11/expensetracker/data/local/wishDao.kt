package com.st11.expensetracker.data.local

//

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.st11.expensetracker.model.WishlistEntity
import kotlinx.coroutines.flow.Flow

//This interface defines the database operations.
@Dao
interface WishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWish(wish: WishlistEntity)

    @Update
    suspend fun updateWish(wish: WishlistEntity)

    @Query("SELECT * FROM wishlist ORDER BY timestamp DESC")
    fun getAllWishlist(): Flow<List<WishlistEntity>>


    @Query("SELECT COUNT(*) FROM wishlist")
    fun getAllTotalWishlist(): Flow<Int?>


    @Query("UPDATE wishlist SET wishStatus = :wishStatus WHERE wishId = :wishId")
    suspend fun updateWishStatus(wishId: String, wishStatus: String): Int?

    @Query("SELECT SUM(estimateAmount) FROM wishlist")
    fun getAllTotalWishlistAmount(): Flow<Float?>



}