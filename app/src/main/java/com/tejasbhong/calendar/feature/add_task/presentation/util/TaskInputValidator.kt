package com.tejasbhong.calendar.feature.add_task.presentation.util

object TaskInputValidator {
    fun isTitleInvalid(title: String?): Boolean = title.isNullOrBlank()
    fun isDescriptionInvalid(description: String?): Boolean = description.isNullOrBlank()
}
