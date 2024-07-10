package com.tejasbhong.calendar.common.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.tejasbhong.calendar.common.domain.model.Task

data class TaskDto(
    @SerializedName("task_detail")
    val taskDetail: TaskDetailDto,
    @SerializedName("task_id")
    val taskId: Int,
) {
    fun toDomain(userId: Int): Task {
        return Task(
            id = taskId,
            userId = userId,
            title = taskDetail.title,
            description = taskDetail.description,
            date = taskDetail.date,
        )
    }
}
