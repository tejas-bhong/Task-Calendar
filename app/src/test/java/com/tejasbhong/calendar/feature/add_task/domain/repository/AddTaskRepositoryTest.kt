package com.tejasbhong.calendar.feature.add_task.domain.repository

import com.tejasbhong.calendar.common.domain.model.Task
import com.tejasbhong.calendar.common.domain.repository.TasksRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class AddTaskRepositoryTest {
    @Mock
    private lateinit var tasksRepository: TasksRepository

    @Before
    fun prepare() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test add task success`() = runTest {
        val task = Task(
            id = null,
            userId = 123,
            title = "Title",
            description = "Description",
            date = System.currentTimeMillis(),
        )
        tasksRepository.addTask(task)
        verify(tasksRepository).addTask(task)
    }
}
