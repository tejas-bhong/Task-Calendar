package com.tejasbhong.calendar.common.data.remote.api

import com.tejasbhong.calendar.common.data.remote.dto.TasksDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TasksApi {
    @Throws(Exception::class)
    @POST("getCalendarTaskList")
    suspend fun getAllTasks(@Body body: GetAllTasksReqBody): Response<TasksDto>

    @Throws(Exception::class)
    @POST("storeCalendarTask")
    suspend fun addTask(@Body body: AddTaskReqBody)

    @Throws(Exception::class)
    @POST("deleteCalendarTask")
    suspend fun deleteTask(@Body body: DeleteTaskReqBody)
}
