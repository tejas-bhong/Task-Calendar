package com.tejasbhong.calendar.common.domain.repository

import com.tejasbhong.calendar.common.domain.model.Task

interface TasksRepository {
    @Throws(Exception::class)
    suspend fun getAllTasks(userId: Int, epoch: Long): List<Task>
    @Throws(Exception::class)
    suspend fun addTask(task: Task)
    @Throws(Exception::class)
    suspend fun deleteTask(task: Task)
}
