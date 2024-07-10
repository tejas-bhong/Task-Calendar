package com.tejasbhong.calendar.common.data.remote.api

import com.google.gson.annotations.SerializedName

data class AddTaskReqBody(
    @SerializedName("task")
    val task: TaskReqBody,
    @SerializedName("user_id")
    val userId: Int,
) {
    data class TaskReqBody(
        @SerializedName("date")
        val date: Long,
        @SerializedName("description")
        val description: String,
        @SerializedName("title")
        val title: String,
    )
}
