package com.st11.expensetracker.viewmodel




import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.st11.expensetracker.model.DailyExpenseTotal
import com.st11.expensetracker.model.ExpenseEntity
import com.st11.expensetracker.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExpenseViewModel(private val expenseRepository: ExpenseRepository) : ViewModel() {

    // Holds the list of expenses
    private val _expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val debts: StateFlow<List<ExpenseEntity>> = _expenses

//    // Holds the list of debt Ids for a specific user
//    private val _debtsId = MutableStateFlow<List<DebtEntity>>(emptyList())
//    val debtsId: StateFlow<List<DebtEntity>> = _debtsId

init {
    getAllExpenses()
}


//    // Holds a single debt item amount Rem
//    private val _debtAmountRem = MutableStateFlow<DebtEntity?>(null)
//    val debtAmountRem: StateFlow<DebtEntity?> = _debtAmountRem

    /**
     * Fetch all debts for a specific user
     */
    private fun getAllExpenses() {
        viewModelScope.launch {
            expenseRepository.getAllExpenses().collectLatest { expenseList ->
                _expenses.value = expenseList
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
     * Insert a new expense record
     */
    fun insertExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            expenseRepository.insertExpense(expense)
        }
    }

    /**
     * Update an existing expense record
     */
    fun updateExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            expenseRepository.updateExpense(expense)
        }
    }



    /**
     * Get a specific debt by its ID
     */
//    fun getAmountRemDebtById(debtId: String) {
//        viewModelScope.launch {
//            _debtAmountRem.value = debtRepository.getDebtAmountRemById(debtId)
//        }
//    }



    private val _totalExpense = MutableStateFlow(0.0f)
    val totalExpense: StateFlow<Float> = _totalExpense

    fun getAllTotalExpense() {
        viewModelScope.launch {
            expenseRepository.getAllTotalExpense().collectLatest { total ->
                _totalExpense.value = total
            }
        }
    }

    private val _totalMonthExpense = MutableStateFlow(0.0f)
    val totalMonthExpense: StateFlow<Float> = _totalMonthExpense

    fun getMonthlyTotalExpense(month: String) {
        viewModelScope.launch {
            expenseRepository.getMonthlyTotalExpense(month).collectLatest { total ->
                _totalMonthExpense.value = total
            }
        }
    }



    val dailyExpenseTotals: StateFlow<List<DailyExpenseTotal>> =
        expenseRepository.getDailyTotalExpenses()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

}