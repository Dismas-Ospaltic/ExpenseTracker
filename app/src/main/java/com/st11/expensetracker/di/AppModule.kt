package com.st11.expensetracker.di


import com.st11.expensetracker.data.datastore.OnboardingPreferencesManager
import com.st11.expensetracker.viewmodel.OnboardingViewModel
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel


val appModule = module {
    // Define ViewModel injection
    viewModel { OnboardingViewModel(get()) }

    // Define PreferencesManager injection
    single { OnboardingPreferencesManager(get()) }

//    single { IdentityPreferences(get()) }
//
//    viewModel { CreateIdentityViewModel(get()) }
//
//    single { AppDatabase.getDatabase(get()).eventDao() }
//    single { EventRepository(get()) }
//    viewModel {  EventsViewModel(get()) }
//
//    single { AppDatabase.getDatabase(get()).inviteeDao() }
//    single { InviteeRepository(get()) }
//    viewModel {  InviteeViewModel(get()) }






}