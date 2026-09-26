package com.budgetsave.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

    fun formatDate(timestamp: Long): String {
        return dateFormat.format(Date(timestamp))
    }

    fun formatMonthYear(year: Int, month: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1)
        return monthYearFormat.format(calendar.time)
    }

    fun getStartOfMonth(year: Int, month: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun getEndOfMonth(year: Int, month: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1, 23, 59, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return calendar.timeInMillis
    }

    fun getCurrentMonthYear(): Pair<Int, Int> {
        val calendar = Calendar.getInstance()
        return Pair(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)
    }

    fun getStartOfToday(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun getEndOfToday(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    fun formatDayMonth(timestamp: Long): String {
        val format = SimpleDateFormat("dd MMM", Locale.getDefault())
        return format.format(Date(timestamp))
    }

    fun formatFullDate(timestamp: Long): String {
        return dateFormat.format(Date(timestamp))
    }
}

object CurrencyUtils {
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

    fun formatCurrency(amount: Double): String {
        return currencyFormat.format(amount)
    }

    fun formatCurrencyCompact(amount: Double): String {
        return when {
            amount >= 1_000_000_000 -> String.format(Locale.getDefault(), "%.1fB", amount / 1_000_000_000)
            amount >= 1_000_000 -> String.format(Locale.getDefault(), "%.1fM", amount / 1_000_000)
            amount >= 1_000 -> String.format(Locale.getDefault(), "%.1fK", amount / 1_000)
            else -> formatCurrency(amount)
        }
    }

    fun parseCurrency(text: String): Double {
        return try {
            text.replace("[^\\d.]".toRegex(), "").toDoubleOrNull() ?: 0.0
        } catch (e: Exception) {
            0.0
        }
    }
}
