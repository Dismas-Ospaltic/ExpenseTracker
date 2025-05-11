package com.st11.expensetracker.domain.usecase

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.st11.expensetracker.data.worker.ReminderWorker
import java.util.concurrent.TimeUnit

object ReminderScheduler {
    fun scheduleReminder(context: Context, hours: Long = (3/60)) {
        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(hours, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}