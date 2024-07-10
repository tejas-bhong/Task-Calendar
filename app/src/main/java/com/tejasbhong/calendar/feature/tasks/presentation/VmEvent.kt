package com.tejasbhong.calendar.feature.tasks.presentation

sealed interface VmEvent {
    data object FailedToLoadTasks: VmEvent
    data object FailedToDeleteTask: VmEvent
    data object TaskDeleted: VmEvent
}
