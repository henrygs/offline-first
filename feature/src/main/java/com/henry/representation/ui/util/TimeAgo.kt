package com.henry.representation.ui.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

fun formatTimeAgo(publishedAt: String?): String {
    if (publishedAt == null) return ""

    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    format.timeZone = TimeZone.getTimeZone("UTC")

    val date = try {
        format.parse(publishedAt)
    } catch (e: Exception) {
        return ""
    } ?: return ""

    val now = Date()
    val diffMs = now.time - date.time

    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMs)
    val hours = TimeUnit.MILLISECONDS.toHours(diffMs)
    val days = TimeUnit.MILLISECONDS.toDays(diffMs)

    return when {
        minutes < 1 -> "now"
        minutes < 60 -> "${minutes}m"
        hours < 24 -> "${hours}h"
        days < 7 -> "${days}d"
        else -> {
            val displayFormat = SimpleDateFormat("MMM d", Locale("pt", "BR"))
            displayFormat.format(date)
        }
    }
}
