package com.example.pinboard

import com.example.pinboard.dagger.ActivityScope
import com.example.pinboard.dagger.OnDestroy
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Completable


@Module
abstract class MainActivityModule {

	@Module
	companion object {
		@JvmStatic
		@Provides
		@OnDestroy
		fun provideOnDestroyCompletable(
				activity: MainActivity): Completable = activity.onDestroyCompletable

	}

	@ActivityScope
	@Binds
	abstract fun bindView(mainActivity: MainActivity): MainContract.View

	@ActivityScope
	@Binds
	abstract fun bindPresenter(presenter: MainPresenter): MainContract.Presenter

}