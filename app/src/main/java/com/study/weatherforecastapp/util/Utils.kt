package com.study.weatherforecastapp.util

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(timestamp: Int): String {
    val sdf = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
    val date = Date(timestamp.toLong() * 1000)
    return sdf.format(date)
}

fun formatDateTime(timestamp: Int): String {
    val sdf = SimpleDateFormat("hh:mm:aa", Locale.getDefault())
    val date = Date(timestamp.toLong() * 1000)
    return sdf.format(date)
}

fun formatDecimal(decimal: Double): String = "%.0f".format(decimal)