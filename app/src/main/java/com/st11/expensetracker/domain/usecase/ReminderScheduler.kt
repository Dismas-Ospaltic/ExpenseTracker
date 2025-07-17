package com.st11.expensetracker.domain.usecase

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.st11.expensetracker.data.worker.ReminderWorker
import java.util.concurrent.TimeUnit

object ReminderScheduler {
//    fun scheduleReminder(context: Context, hours: Long) {
//        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(hours, TimeUnit.HOURS)
//            .build()
//
//        WorkManager.getInstance(context).enqueue(workRequest)
//    }

    fun scheduleReminder(context: Context, hours: Long) {
        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(hours, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "reminder_worker", // 🔥 Unique name for cancellation/replacement
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }



    fun cancelReminder(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork("reminder_worker")
    }

}