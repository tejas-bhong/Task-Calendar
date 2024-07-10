package com.tejasbhong.calendar.feature.add_task.presentation

sealed interface VmEvent {
    data object TaskSaved: VmEvent
    data object FailedToSaveTask: VmEvent
    data object Saving: VmEvent
    data object Idle: VmEvent
}
