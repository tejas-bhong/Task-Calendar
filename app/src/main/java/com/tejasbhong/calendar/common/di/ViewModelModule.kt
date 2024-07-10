package com.tejasbhong.calendar.common.di

import com.tejasbhong.calendar.BuildConfig
import com.tejasbhong.calendar.common.data.remote.api.TasksApi
import com.tejasbhong.calendar.common.data.remote.repository.TasksRepositoryImpl
import com.tejasbhong.calendar.common.domain.repository.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideGetTasksApi(): TasksApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(TasksApi::class.java)
    }

    @Provides
    fun provideGetTasksRepository(tasksApi: TasksApi): TasksRepository {
        return TasksRepositoryImpl(tasksApi)
    }
}
