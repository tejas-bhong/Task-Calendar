package com.tejasbhong.calendar.feature.tasks.domain.use_case

import com.tejasbhong.calendar.common.domain.model.Task
import com.tejasbhong.calendar.common.domain.repository.TasksRepository
import javax.inject.Inject

class DeleteTask @Inject constructor(
    private val tasksRepository: TasksRepository,
) {
    @Throws(Exception::class)
    suspend operator fun invoke(task: Task) {
        tasksRepository.deleteTask(task)
    }
}
