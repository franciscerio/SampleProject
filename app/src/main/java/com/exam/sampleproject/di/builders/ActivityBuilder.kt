package com.exam.sampleproject.di.builders

import com.exam.sampleproject.features.main.MainActivity
import com.exam.sampleproject.di.scopes.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}
