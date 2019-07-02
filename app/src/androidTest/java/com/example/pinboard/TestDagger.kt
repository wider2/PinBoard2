package com.example.pinboard

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.pinboard.PinBoardApplication.Companion.getInstance
import com.example.pinboard.dagger.DaggerApplicationComponent
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestDagger {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java, true, false)


    @Test
    fun setUp() {

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as PinBoardApplication

        val testComponent = DaggerApplicationComponent.builder()
            .pinApplicationBind(getInstance())
            .build()

        app.component = testComponent

        assertNotNull(app)
    }

}