package com.example.pinboard

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v4.app.Fragment
import com.example.pinboard.details.DetailsFragment
import com.example.pinboard.pins.PinsFragment
import com.example.pinboard.utils.ConnectivityReceiver
import com.example.pinboard.utils.Utilities
import com.example.pinboard.utils.addFragmentSafely
import com.example.pinboard.utils.replaceFragmentSafely
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    // Context of the app under test.
    lateinit var appContext: Context

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)


    @Rule
    fun rule() = activityTestRule


    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getTargetContext()
    }

    @After
    fun tearDown() {
        activityTestRule.finishActivity()
    }

    @Test
    fun useAppContext() {
        assertEquals("com.example.pinboard", appContext.packageName)
    }

    @Test
    fun checkConnection() {
        val connect = ConnectivityReceiver().isConnectedOrConnecting(appContext)
        assertTrue(connect)
    }

    @Test
    fun testWebPage() {
        assertNotNull(Utilities().openWebPage("https://www.google.com/", appContext))
    }

    //check extension methods
    @Test
    fun addFragment() {
        val testFragment: Fragment = activityTestRule.activity.addFragmentSafely(
            fragment = PinsFragment(),
            tag = PinsFragment::class.java.simpleName,
            containerViewId = R.id.fragment_container,
            allowStateLoss = true
        )
        assertNotNull(testFragment)
    }

    @Test
    fun replaceFragment() {
        val testFragment = rule().activity.replaceFragmentSafely(
            fragment = DetailsFragment(),
            tag = DetailsFragment::class.java.simpleName,
            containerViewId = R.id.fragment_container,
            allowStateLoss = true
        )
        assertNotNull(testFragment)
    }

}