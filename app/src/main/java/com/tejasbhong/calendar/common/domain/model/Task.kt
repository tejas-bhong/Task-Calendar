package com.tejasbhong.calendar.common.domain.model

data class Task(
    val id: Int? = null,
    val userId: Int,
    val title: String,
    val description: String,
    val date: Long,
)
