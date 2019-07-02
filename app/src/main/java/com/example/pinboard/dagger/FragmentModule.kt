package com.example.pinboard.dagger

import com.example.pinboard.pins.PinsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

	@ContributesAndroidInjector
	abstract fun contributeMainFragment(): PinsFragment

}