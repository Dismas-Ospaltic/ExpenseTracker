package com.st11.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.st11.expensetracker.domain.usecase.ReminderScheduler

//class MainViewModel(application: Application) : AndroidViewModel(application) {
//    fun startReminderWorker(hours: Long = 3) {
//        ReminderScheduler.scheduleReminder(getApplication(), hours)
//    }
//}

class MainViewModel(
    application: Application,
    private val reminderScheduler: ReminderScheduler
) : AndroidViewModel(application) {

    fun startReminderWorker(hours: Long = (3/60)) {
        reminderScheduler.scheduleReminder(getApplication(), hours)
    }
}