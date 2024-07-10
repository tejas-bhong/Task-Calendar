package com.tejasbhong.calendar.common.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TasksDto(
    @SerializedName("tasks")
    val tasks: List<TaskDto>,
)
