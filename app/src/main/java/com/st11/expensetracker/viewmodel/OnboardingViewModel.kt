package com.st11.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.st11.expensetracker.data.datastore.OnboardingPreferencesManager
import kotlinx.coroutines.launch

class OnboardingViewModel(private val preferencesManager: OnboardingPreferencesManager) : ViewModel() {

    // Exposed Flow for Onboarding state
    val isOnboardingCompleted = preferencesManager.isOnboardingCompleted

    // Method to complete onboarding
    fun completeOnboarding() {
        viewModelScope.launch {
            preferencesManager.completeOnboarding()
        }
    }
}