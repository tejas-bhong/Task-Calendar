package com.tejasbhong.calendar.feature.tasks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tejasbhong.calendar.common.domain.model.Task
import com.tejasbhong.calendar.feature.tasks.domain.use_case.DeleteTask
import com.tejasbhong.calendar.feature.tasks.domain.use_case.GetAllTasks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getAllTasks: GetAllTasks,
    private val deleteTask: DeleteTask,
) : ViewModel() {
    private val _uiData = MutableStateFlow(UiData())
    val uiData = _uiData.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiData(),
    )
    private val _vmEvent = Channel<VmEvent>()
    val vmEvent = _vmEvent.receiveAsFlow()

    fun fetchAllTasks(userId: Int, epoch: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiData.update {
                it.copy(
                    tasks = try {
                        getAllTasks(userId = userId, epoch = epoch)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        _vmEvent.trySend(VmEvent.FailedToLoadTasks)
                        emptyList()
                    }
                )
            }
        }
    }

    fun onDeleteClick(task: Task, epoch: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteTask(task)
                fetchAllTasks(userId = task.userId, epoch = epoch)
                _vmEvent.trySend(VmEvent.TaskDeleted)
            } catch (e: Exception) {
                e.printStackTrace()
                _vmEvent.trySend(VmEvent.FailedToDeleteTask)
            }
        }
    }
}
