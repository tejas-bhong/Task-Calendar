package com.tejasbhong.calendar.common.data.remote.api

import com.google.gson.annotations.SerializedName

data class GetAllTasksReqBody(
    @SerializedName("user_id")
    val userId: Int,
)
