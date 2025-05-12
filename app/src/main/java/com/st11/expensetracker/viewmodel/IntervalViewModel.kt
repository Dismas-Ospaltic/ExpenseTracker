package com.st11.expensetracker.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.st11.expensetracker.data.datastore.IntervalPreferences
import com.st11.expensetracker.data.datastore.UserTimeData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IntervalViewModel(
    private val intervalPreferences: IntervalPreferences
) : ViewModel() {

    // Expose time interval as StateFlow (better for Compose)
    val userTimeInterval: StateFlow<UserTimeData> =
        intervalPreferences.userTimeData
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = UserTimeData(1L)
            )

    // Save time interval (e.g., from user settings)
    fun saveTimeInterval(interval: Long) {
        viewModelScope.launch {
            intervalPreferences.saveUserData(interval)
        }
    }

    // Optional: Clear preferences
    fun clearUserTimeData() {
        viewModelScope.launch {
            intervalPreferences.clearUserData()
        }
    }
}
