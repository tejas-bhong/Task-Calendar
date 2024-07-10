package com.tejasbhong.calendar.feature.calendar.presentation.utils

import com.tejasbhong.calendar.feature.calendar.domain.model.CalendarItem
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar

class CalendarUtilsTest {
    @Test
    fun testGenerateMonthData() {
        val currentCalendar = Calendar.getInstance()
        val currentMonth = currentCalendar.get(Calendar.MONTH)
        val currentYear = currentCalendar.get(Calendar.YEAR)
        val targetMonth = Calendar.JULY
        val targetYear = 2024

        val monthOffset = (targetYear - currentYear) * 12 + (targetMonth - currentMonth)
        val calendarItems = CalendarUtils.generateMonthData(monthOffset)

        // Assuming we're testing for July 2024, which starts on a Tuesday (2024-07-01)
        // Check the first day of the month
        val firstItem = calendarItems.first()
        assertTrue(firstItem is CalendarItem.Empty) // First item should be from previous month

        // Check 1st day in the month (e.g., July 1, 2024)
        val middleItem = calendarItems.drop(1).first()
        assertTrue(middleItem is CalendarItem.DayOfMonth)

        // Check the last day of the month (e.g., July 31, 2024)
        val lastItem = calendarItems.last()
        assertTrue(lastItem is CalendarItem.Empty) // Last items should be from next month
    }
}
