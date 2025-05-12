package com.st11.expensetracker.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDateToReadable(input: String): String? {
    val possibleFormats = listOf("yyyy-MM-dd", "dd-MM-yyyy")

    for (format in possibleFormats) {
        try {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            val date = sdf.parse(input) ?: continue

            val calendar = Calendar.getInstance().apply { time = date }
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val suffix = getDaySuffix(day)

            val outputFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
            val monthYear = outputFormat.format(date)

            return "$day$suffix $monthYear"
        } catch (e: Exception) {
            // try next format
        }
    }

    return null // invalid format
}

fun getDaySuffix(day: Int): String {
    return if (day in 11..13) {
        "th"
    } else when (day % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}
