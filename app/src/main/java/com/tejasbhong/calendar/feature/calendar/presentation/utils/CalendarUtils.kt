package com.tejasbhong.calendar.feature.calendar.presentation.utils

import com.tejasbhong.calendar.feature.calendar.domain.model.CalendarItem
import java.util.Calendar

object CalendarUtils {
    private val today = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    fun generateMonthData(monthOffset: Int): List<CalendarItem> {
        val calendarItems = mutableListOf<CalendarItem>()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.add(Calendar.MONTH, monthOffset)

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        // First day of the current month
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
//        val firstDayOfWeekAdjusted = (firstDayOfWeek - calendar.firstDayOfWeek + 7) % 7 + 1

        // Number of days in the current month
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Set calendar to the previous month
        calendar.add(Calendar.MONTH, -1)

        // Fill dates from the previous month
        for (i in firstDayOfWeek - 1 downTo 1) {
            calendar.set(Calendar.DAY_OF_MONTH, i)
            calendarItems.add(
                CalendarItem.Empty(calendar.timeInMillis)
            )
        }

        // Reset calendar to the current month
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH))

        // Fill dates of the current month
        for (i in 1..daysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, i)
            val epoch = calendar.timeInMillis
            calendarItems.add(
                CalendarItem.DayOfMonth(
                    epoch = epoch,
                    isToday = isToday(epoch),
                )
            )
        }

        // Fill dates from the next month
        val totalDays = 7 * 5 // Total grid slots (7 columns * 5 rows)
        val remainingDays = totalDays - calendarItems.size

        calendar.add(Calendar.MONTH, 1)
        for (i in 1..remainingDays) {
            calendar.set(Calendar.DAY_OF_MONTH, i)
            calendarItems.add(CalendarItem.Empty(calendar.timeInMillis))
        }

        return calendarItems
    }

    fun isToday(epoch: Long): Boolean {
        return today.timeInMillis == epoch
    }
}
