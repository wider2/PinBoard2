package com.example.pinboard

import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.pinboard.dagger.ApplicationComponent
import com.example.pinboard.dagger.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class PinBoardApplication : Application(), HasActivityInjector {

    lateinit var component: ApplicationComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    init {
        instance = this
    }

    companion object {
        private var instance: PinBoardApplication? = null

        fun getInstance(): PinBoardApplication {
            return instance!!
        }
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
            .pinApplicationBind(this)
            .build()
        component.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

}