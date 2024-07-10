package com.tejasbhong.calendar.feature.tasks.domain.repository

import com.tejasbhong.calendar.common.domain.model.Task
import com.tejasbhong.calendar.common.domain.repository.TasksRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class DeleteTaskTest {
    @Mock
    private lateinit var tasksRepository: TasksRepository

    @Before
    fun prepare() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test delete task success`() = runTest {
        val task = Task(
            id = null,
            userId = 123,
            title = "Title",
            description = "Description",
            date = System.currentTimeMillis(),
        )
        tasksRepository.deleteTask(task)
        verify(tasksRepository).deleteTask(task)
    }
}
