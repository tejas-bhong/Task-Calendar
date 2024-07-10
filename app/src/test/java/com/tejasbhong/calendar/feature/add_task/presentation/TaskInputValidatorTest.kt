package com.tejasbhong.calendar.feature.add_task.presentation

import com.tejasbhong.calendar.feature.add_task.presentation.util.TaskInputValidator
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TaskInputValidatorTest {
    @Test
    fun `test valid data`() = runTest {
        val title = "Valid Title"
        val description = "Valid Description"
        assertFalse(TaskInputValidator.isTitleInvalid(title))
        assertFalse(TaskInputValidator.isTitleInvalid(description))
    }

    @Test
    fun `test invalid empty title`() = runTest {
        val title = ""
        assertTrue(TaskInputValidator.isTitleInvalid(title))
    }

    @Test
    fun `test invalid blank title`() = runTest {
        val title = "  "
        assertTrue(TaskInputValidator.isTitleInvalid(title))
    }

    @Test
    fun `test invalid empty description`() = runTest {
        val description = ""
        assertTrue(TaskInputValidator.isTitleInvalid(description))
    }

    @Test
    fun `test invalid blank description`() = runTest {
        val description = "  "
        assertTrue(TaskInputValidator.isTitleInvalid(description))
    }
}
