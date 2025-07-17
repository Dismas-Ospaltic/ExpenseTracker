package com.st11.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.st11.expensetracker.domain.usecase.ReminderScheduler



class MainViewModel(
    application: Application,
    private val reminderScheduler: ReminderScheduler
) : AndroidViewModel(application) {

    fun startReminderWorker(hours: Long) {
        reminderScheduler.scheduleReminder(getApplication(), hours)
    }

    fun stopReminderWorker() {
        reminderScheduler.cancelReminder(getApplication())
    }

}