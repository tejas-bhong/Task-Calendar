package com.tejasbhong.calendar.feature.tasks.domain.use_case

import com.tejasbhong.calendar.common.domain.model.Task
import com.tejasbhong.calendar.common.domain.repository.TasksRepository
import javax.inject.Inject

class GetAllTasks @Inject constructor(
    private val tasksRepository: TasksRepository,
) {
    @Throws(Exception::class)
    suspend operator fun invoke(userId: Int, epoch: Long): List<Task> {
        return tasksRepository.getAllTasks(userId, epoch)
    }
}
