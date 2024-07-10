package com.tejasbhong.calendar.feature.calendar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tejasbhong.calendar.feature.calendar.domain.model.CalendarItem
import com.tejasbhong.calendar.feature.calendar.domain.model.CalendarItem.DayOfMonth
import com.tejasbhong.calendar.feature.calendar.presentation.utils.CalendarUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
) : ViewModel() {
    private val _uiData = MutableStateFlow(UiData())
    val uiData = _uiData.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiData(),
    )
    var monthAndYears = mutableListOf<String>()
    private val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

    init {
        loadCalendarData()
    }

    private fun loadCalendarData() {
        viewModelScope.launch(Dispatchers.Default) {
            val monthsBeforeAndAfter = 24
            val monthsData = mutableListOf<List<CalendarItem>>()
            for (monthOffset in -monthsBeforeAndAfter..monthsBeforeAndAfter) {
                val monthData = CalendarUtils.generateMonthData(monthOffset)
                monthsData.add(monthData)
                monthData.filterIsInstance<DayOfMonth>()
                    .drop(7)
                    .first()
                    .let { monthAndYears.add(monthYearFormat.format(it.epoch)) }
            }
            _uiData.update { uiData: UiData ->
                uiData.copy(monthsData = monthsData)
            }
        }
    }
}
