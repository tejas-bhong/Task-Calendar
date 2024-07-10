package com.tejasbhong.calendar.feature.add_task.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tejasbhong.calendar.common.domain.model.Task
import com.tejasbhong.calendar.feature.add_task.domain.use_case.AddTask
import com.tejasbhong.calendar.feature.add_task.presentation.util.TaskInputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTask: AddTask,
) : ViewModel() {
    private val _uiData = MutableStateFlow(UiData())
    val uiData = _uiData.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiData(),
    )
    private val _vmEvent = Channel<VmEvent>()
    val vmEvent = _vmEvent.receiveAsFlow()
    private var onSaveClickJob: Job? = null

    fun onSaveClick(selectedDate: Long, title: String?, description: String?) {
        _vmEvent.trySend(VmEvent.Saving)
        onSaveClickJob?.cancel()
        onSaveClickJob = viewModelScope.launch(Dispatchers.Default) {
            delay(500)
            val isTitleInvalid = TaskInputValidator.isTitleInvalid(title)
            val isDescriptionInvalid = TaskInputValidator.isDescriptionInvalid(description)
            _uiData.update {
                UiData(
                    isTitleInvalid = isTitleInvalid,
                    isDescriptionInvalid = isDescriptionInvalid,
                )
            }
            if (isTitleInvalid || isDescriptionInvalid) {
                _vmEvent.trySend(VmEvent.Idle)
                return@launch
            }
            try {
                val task = Task(
                    userId = 123,
                    title = title!!,
                    description = description!!,
                    date = selectedDate,
                )
                addTask(task)
                _vmEvent.trySend(VmEvent.TaskSaved)
            } catch (e: Exception) {
                e.printStackTrace()
                _vmEvent.trySend(VmEvent.FailedToSaveTask)
            }
        }
    }
}
