package com.tejasbhong.calendar.common.data.remote.repository

import com.tejasbhong.calendar.common.data.remote.api.AddTaskReqBody
import com.tejasbhong.calendar.common.data.remote.api.DeleteTaskReqBody
import com.tejasbhong.calendar.common.data.remote.api.GetAllTasksReqBody
import com.tejasbhong.calendar.common.data.remote.api.TasksApi
import com.tejasbhong.calendar.common.data.remote.dto.TaskDto
import com.tejasbhong.calendar.common.domain.model.Task
import com.tejasbhong.calendar.common.domain.repository.TasksRepository
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val tasksApi: TasksApi,
) : TasksRepository {
    @Throws(Exception::class)
    override suspend fun getAllTasks(userId: Int, epoch: Long): List<Task> {
        val response = tasksApi.getAllTasks(GetAllTasksReqBody(userId))
        return if (response.isSuccessful) {
            val tasks = response.body()?.tasks?.map { taskDto: TaskDto ->
                taskDto.toDomain(userId)
            } ?: emptyList()
            tasks.filter { task: Task -> task.date == epoch }
        } else {
            println(response.errorBody()?.string())
            emptyList()
        }
    }

    @Throws(Exception::class)
    override suspend fun addTask(task: Task) {
        tasksApi.addTask(
            AddTaskReqBody(
                AddTaskReqBody.TaskReqBody(
                    task.date,
                    task.description,
                    task.title,
                ),
                task.userId,
            )
        )
    }

    @Throws(Exception::class)
    override suspend fun deleteTask(task: Task) {
        tasksApi.deleteTask(DeleteTaskReqBody(task.id!!, task.userId))
    }
}
