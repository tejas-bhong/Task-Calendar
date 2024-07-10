package com.tejasbhong.calendar.feature.calendar.presentation

import com.tejasbhong.calendar.feature.calendar.domain.model.CalendarItem

data class UiData(
    val monthsData: List<List<CalendarItem>> = emptyList(),
)
