package com.st11.expensetracker.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.st11.expensetracker.utils.formatDate

@Entity(tableName = "wishlist")
data class WishlistEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val wishlistDate: String = formatDate(System.currentTimeMillis()), //DD-MM-YYYY
    val itemName: String,
    val estimateAmount: Float,
    val targetDate: String,
    val wishDescription: String,
    val wishNote: String = null.toString(),
    val priority: String,
    val wishId: String,
    val itemPurchaseDate: String = null.toString(),
    val wishStatus: String = "pending",
    val timestamp: Long = System.currentTimeMillis()
)
