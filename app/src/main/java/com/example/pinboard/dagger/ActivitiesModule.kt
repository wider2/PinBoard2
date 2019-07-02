package com.example.pinboard.dagger

import com.example.pinboard.MainActivity
import com.example.pinboard.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

	@ActivityScope
	@ContributesAndroidInjector(modules = [(MainActivityModule::class)])
	abstract fun bindMainActivity(): MainActivity

}