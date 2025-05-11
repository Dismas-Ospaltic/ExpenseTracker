package com.st11.expensetracker.di


import com.st11.expensetracker.data.datastore.CurrencyPreferences
import com.st11.expensetracker.data.datastore.IntervalPreferences
import com.st11.expensetracker.data.datastore.OnboardingPreferencesManager
import com.st11.expensetracker.data.local.AppDatabase
import com.st11.expensetracker.repository.ExpenseRepository
import com.st11.expensetracker.repository.WishlistRepository
import com.st11.expensetracker.viewmodel.CurrencyViewModel
import com.st11.expensetracker.viewmodel.ExpenseViewModel
import com.st11.expensetracker.viewmodel.IntervalViewModel
import com.st11.expensetracker.viewmodel.MainViewModel
import com.st11.expensetracker.viewmodel.OnboardingViewModel
import com.st11.expensetracker.viewmodel.WishlistViewModel
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel


val appModule = module {
    // Define ViewModel injection
    viewModel { OnboardingViewModel(get()) }

    // Define PreferencesManager injection
    single { OnboardingPreferencesManager(get()) }


    // Define ViewModel injection
    viewModel { CurrencyViewModel(get()) }

    // Define PreferencesManager injection
    single { CurrencyPreferences(get()) }


    // Define ViewModel injection
    viewModel { IntervalViewModel(get()) }

    // Define PreferencesManager injection
    single { IntervalPreferences(get()) }

    // Provide ReminderScheduler
    single { com.st11.expensetracker.domain.usecase.ReminderScheduler }

    // Provide MainViewModel
    viewModel { MainViewModel(get(), get()) } // get() for Application and ReminderScheduler

//    single { IdentityPreferences(get()) }
//
//    viewModel { CreateIdentityViewModel(get()) }
//
    single { AppDatabase.getDatabase(get()).expenseDao() }
    single { ExpenseRepository(get()) }
    viewModel {  ExpenseViewModel(get()) }

    single { AppDatabase.getDatabase(get()).wishDao() }
    single { WishlistRepository(get()) }
    viewModel {  WishlistViewModel(get()) }






}