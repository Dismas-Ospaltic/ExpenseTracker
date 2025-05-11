package com.st11.expensetracker.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.st11.expensetracker.R


class ReminderWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        showNotification("Expense Reminder", "Did you Spend anything today? Add it now to your expenses")
        return Result.success()
    }

//    private fun showNotification(title: String, message: String) {
//        val channelId = "reminder_channel"
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                channelId,
//                "Reminder Notifications",
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            manager.createNotificationChannel(channel)
//        }
//
//        val builder = NotificationCompat.Builder(applicationContext, channelId)
//            .setSmallIcon(R.drawable.budget) // Your icon
//            .setContentTitle(title)
//            .setContentText(message)
//            .setAutoCancel(true)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.POST_NOTIFICATIONS)
//                == PackageManager.PERMISSION_GRANTED) {
//
//                with(NotificationManagerCompat.from(applicationContext)) {
//                    notify(1001, builder.build())
//                }
//
//            } else {
//                // Permission not granted, do not show notification
//            }
//        } else {
//            // For Android 12 and below (no permission required)
//            with(NotificationManagerCompat.from(applicationContext)) {
//                notify(1001, builder.build())
//            }
//        }
//
//
//    }

    private fun showNotification(title: String, message: String) {
        val channelId = "reminder_channel"

        // Create a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Reminder Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        // 🔥 Create Intent to open MainActivity
        val intent = applicationContext.packageManager
            .getLaunchIntentForPackage(applicationContext.packageName)
        intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 🔥 Attach the intent to the notification
        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.budget)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // 🔥 Open app when clicked

        // Show notification if permission is granted (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
                with(NotificationManagerCompat.from(applicationContext)) {
                    notify(1001, builder.build())
                }
            }
        } else {
            with(NotificationManagerCompat.from(applicationContext)) {
                notify(1001, builder.build())
            }
        }
    }

}