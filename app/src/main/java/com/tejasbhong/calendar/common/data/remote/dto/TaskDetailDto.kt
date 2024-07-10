package com.tejasbhong.calendar.common.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TaskDetailDto(
    @SerializedName("date")
    val date: Long,
    @SerializedName("description")
    val description: String,
    @SerializedName("title")
    val title: String
)
