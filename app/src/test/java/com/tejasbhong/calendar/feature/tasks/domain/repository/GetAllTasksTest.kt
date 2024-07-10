package com.tejasbhong.calendar.feature.tasks.domain.repository

import com.tejasbhong.calendar.common.domain.model.Task
import com.tejasbhong.calendar.common.domain.repository.TasksRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetAllTasksTest {
    @Mock
    private lateinit var tasksRepository: TasksRepository

    @Before
    fun prepare() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test get all tasks`() = runTest {
        val epoch = System.currentTimeMillis()
        `when`(tasksRepository.getAllTasks(123, epoch)).thenReturn(
            listOf(
                Task(
                    id = null,
                    userId = 123,
                    title = "Title",
                    description = "Description",
                    date = epoch,
                )
            )
        )
        tasksRepository.getAllTasks(123, epoch)
        verify(tasksRepository).getAllTasks(123, epoch)
    }
}
