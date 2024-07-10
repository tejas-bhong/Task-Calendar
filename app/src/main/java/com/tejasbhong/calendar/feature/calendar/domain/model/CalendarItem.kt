package com.tejasbhong.calendar.feature.calendar.domain.model

sealed class CalendarItem(open val epoch: Long) {
    data class DayOfMonth(
        override val epoch: Long,
        val isToday: Boolean = false,
        val tasksCount: Int = 0,
    ) : CalendarItem(epoch)

    data class Empty(override val epoch: Long) : CalendarItem(epoch)
}
