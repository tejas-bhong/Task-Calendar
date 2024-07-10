package com.tejasbhong.calendar.common.data.remote.api

import com.google.gson.annotations.SerializedName

data class DeleteTaskReqBody(
    @SerializedName("task_id")
    val taskId: Int,
    @SerializedName("user_id")
    val userId: Int,
)
