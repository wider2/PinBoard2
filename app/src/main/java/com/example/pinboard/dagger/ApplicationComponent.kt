package com.example.pinboard.dagger

import com.example.pinboard.PinBoardApplication
import com.example.pinboard.data.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        ActivitiesModule::class,
        FragmentModule::class, DataModule::class]
)
interface ApplicationComponent {

    fun inject(pinApplication: PinBoardApplication)

    @Component.Builder
    interface Builder {

        fun build(): ApplicationComponent

        @BindsInstance
        fun pinApplicationBind(pinApplication: PinBoardApplication): Builder
    }

}