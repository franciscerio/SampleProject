package com.exam.sampleproject.di

import com.exam.sampleproject.utils.schedulers.BaseSchedulerProvider
import com.exam.sampleproject.utils.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulerModule {

    @Provides
    @Singleton
    fun providesSchedulerSource(): BaseSchedulerProvider =
            SchedulerProvider.getInstance()
}
