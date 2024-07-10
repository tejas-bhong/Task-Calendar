package com.tejasbhong.calendar.feature.tasks.presentation

import com.tejasbhong.calendar.common.domain.model.Task

data class UiData(
    val tasks: List<Task> = emptyList(),
)
