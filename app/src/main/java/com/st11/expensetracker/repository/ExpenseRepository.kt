package com.st11.expensetracker.repository


import com.st11.expensetracker.data.local.ExpenseDao
import com.st11.expensetracker.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    //    val allDebt: Flow<List<DebtEntity>> = debtDao.getAllDebt(userId)
    fun getAllExpenses(): Flow<List<ExpenseEntity>> = expenseDao.getAllExpenses()



    suspend fun insertExpense(expense: ExpenseEntity) {
        expenseDao.insertExpense(expense)
    }

    suspend fun updateExpense(expense: ExpenseEntity) {
        expenseDao.updateExpense(expense)
    }


    fun getAllTotalExpense(): Flow<Float> {
        return expenseDao.getAllTotalExpense()
            .map { total -> total ?: 0.0f }  // Convert NULL to 0.0
    }

//    fun getAllTotalPaidDebt(): Flow<Int> {
//        return debtDao.getAllTotalPaidDebt()
//            .map { total -> total ?: 0 }  // Convert NULL to 0.0
//    }








}
