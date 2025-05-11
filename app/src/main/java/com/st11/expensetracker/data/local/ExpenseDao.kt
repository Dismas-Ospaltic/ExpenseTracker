package com.st11.expensetracker.data.local

//
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.st11.expensetracker.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow

//This interface defines the database operations.
@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses ORDER BY timestamp DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>


    @Query("SELECT SUM(expenseAmount) FROM expenses")
    fun getAllTotalExpense(): Flow<Float?>


}