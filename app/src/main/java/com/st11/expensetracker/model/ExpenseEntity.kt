package com.st11.expensetracker.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.st11.expensetracker.utils.formatDate

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val expenseDate: String = formatDate(System.currentTimeMillis()), //DD-MM-YYYY
    val expenseAmount: Double,
    val expenseCategory: String,
    val expenseDescription: String,
    val paymentMode: String,
    val expenseId: String,
    val timestamp: Long = System.currentTimeMillis()
)